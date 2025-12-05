package com.labs.lab1.security;

import com.labs.lab1.model.SessionStatus;
import com.labs.lab1.model.UserSession;
import com.labs.lab1.repository.UserSessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;
    // Добавляем репозиторий для проверки статуса
    private final UserSessionRepository userSessionRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            username = jwtCore.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // --- НОВАЯ ПРОВЕРКА ЧЕРЕЗ БАЗУ ---
                // Ищем сессию по Access токену
                var session = userSessionRepository.findByAccessToken(jwt);

                // Если сессии нет или она не ACTIVE (например, REVOKED) -> не пускаем
                boolean isSessionValid = session
                        .map(s -> s.getStatus() == SessionStatus.ACTIVE)
                        .orElse(false);

                // Проверяем и криптографию, и базу данных
                if (jwtCore.isTokenValid(jwt, userDetails) && isSessionValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Ошибка валидации
        }

        filterChain.doFilter(request, response);
    }
}