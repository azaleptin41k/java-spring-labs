package com.labs.lab1.service;

import com.labs.lab1.dto.LoginRequest;
import com.labs.lab1.dto.TokenResponse;
import com.labs.lab1.model.Role;
import com.labs.lab1.model.SessionStatus;
import com.labs.lab1.model.User;
import com.labs.lab1.model.UserSession;
import com.labs.lab1.repository.UserRepository;
import com.labs.lab1.repository.UserSessionRepository;
import com.labs.lab1.security.CustomUserDetailsService;
import com.labs.lab1.security.JwtCore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    // --- РЕГИСТРАЦИЯ (Исправлено: добавлена проверка пароля) ---
    public User register(String username, String password, Role role) {
        // 1. Проверка уникальности имени
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        // 2. ВАЛИДАЦИЯ ПАРОЛЯ (Вернули этот кусок!)
        if (!isValidPassword(password)) {
            throw new RuntimeException("Пароль должен быть не менее 8 символов и содержать спецсимволы (!@#$...)");
        }

        // 3. Создание пользователя
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    // --- ЛОГИН (Вход в систему) ---
    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtCore.generateAccessToken(userDetails);
        String refreshToken = jwtCore.generateRefreshToken(userDetails);

        saveNewSession(userDetails.getUsername(), accessToken, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    // --- REFRESH (Обновление токенов) ---
    public TokenResponse refresh(String refreshToken) {
        String username;
        try {
            username = jwtCore.extractUsername(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("Некорректный Refresh токен");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (!jwtCore.isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Refresh токен просрочен или невалиден");
        }

        UserSession session = userSessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Сессия не найдена"));

        if (session.getStatus() == SessionStatus.USED) {
            revokeAllUserSessions(username);
            throw new RuntimeException("Попытка повторного использования токена! Все сессии сброшены.");
        }

        if (session.getStatus() == SessionStatus.REVOKED) {
            throw new RuntimeException("Сессия отозвана. Войдите заново.");
        }

        String newAccessToken = jwtCore.generateAccessToken(userDetails);
        String newRefreshToken = jwtCore.generateRefreshToken(userDetails);

        session.setStatus(SessionStatus.USED);
        userSessionRepository.save(session);

        saveNewSession(username, newAccessToken, newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    // --- ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ---

    // Метод проверки пароля (Вернули!)
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    private void saveNewSession(String username, String accessToken, String refreshToken) {
        UserSession session = UserSession.builder()
                .userEmail(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(SessionStatus.ACTIVE)
                .accessTokenExpiry(Instant.now().plusMillis(jwtCore.getAccessExpiration()))
                .refreshTokenExpiry(Instant.now().plusMillis(jwtCore.getRefreshExpiration()))
                .build();

        userSessionRepository.save(session);
    }

    private void revokeAllUserSessions(String username) {
        List<UserSession> sessions = userSessionRepository.findAllByUserEmail(username);
        for (UserSession session : sessions) {
            if (session.getStatus() == SessionStatus.ACTIVE) {
                session.setStatus(SessionStatus.REVOKED);
                userSessionRepository.save(session);
            }
        }
    }
}