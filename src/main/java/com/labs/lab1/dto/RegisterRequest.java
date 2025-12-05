package com.labs.lab1.dto;

public record RegisterRequest(
        String username,
        String password,
        String role // Роль передаем строкой ("ADMIN" или "USER")
) {}