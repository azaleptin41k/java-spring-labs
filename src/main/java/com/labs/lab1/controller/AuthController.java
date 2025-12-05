package com.labs.lab1.controller;

import com.labs.lab1.dto.LoginRequest;
import com.labs.lab1.dto.RefreshRequest;
import com.labs.lab1.dto.RegisterRequest;
import com.labs.lab1.dto.TokenResponse;
import com.labs.lab1.model.Role;
import com.labs.lab1.model.User;
import com.labs.lab1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 1. РЕГИСТРАЦИЯ
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Логика выбора роли: если прислали "ADMIN" — будет админ, иначе USER.
            Role role = (request.role() != null && request.role().equalsIgnoreCase("ADMIN"))
                    ? Role.ADMIN
                    : Role.USER;

            User user = authService.register(request.username(), request.password(), role);

            return ResponseEntity.ok("User registered successfully: " + user.getUsername());

        } catch (RuntimeException e) {
            // Ловим ошибки (дубликат имени, слабый пароль) и возвращаем 400 Bad Request с текстом
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- 2. ВХОД (Новый функционал JWT) ---
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Здесь обработку ошибок можно доверить GlobalExceptionHandler,
        // но если логин/пароль неверны, Spring сам кинет 401 или 403.
        return ResponseEntity.ok(authService.login(request));
    }

    // --- 3. ОБНОВЛЕНИЕ ТОКЕНА (Новый функционал JWT) ---
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        // Вызываем метод refresh из сервиса
        return ResponseEntity.ok(authService.refresh(request.refreshToken()));
    }
}