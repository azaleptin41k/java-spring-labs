package com.labs.lab1.controller;

import com.labs.lab1.model.Role;
import com.labs.lab1.model.User;
import com.labs.lab1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Временный DTO класс прямо здесь для удобства
    // В реальном проекте вынесите его в отдельный файл
    public record RegisterRequest(String username, String password, String role) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // По умолчанию создаем USER, если не указано иное (или можно сделать логику сложнее)
            Role role = (request.role != null && request.role.equals("ADMIN")) ? Role.ADMIN : Role.USER;

            User user = authService.register(request.username, request.password, role);
            return ResponseEntity.ok("User registered successfully: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}