package com.labs.lab1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    /**
     * Эндпоинт для проверки, что сервер запущен и отвечает.
     * Доступен по адресу: http://localhost:8080/
     */
    @GetMapping("/")
    public String checkServerStatus() {
        return "Server is running!";
    }
}