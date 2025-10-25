package com.labs.lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api") // Все эндпоинты в этом классе будут начинаться с /api
public class ApiController {

    /**
     * Приветствие пользователя.
     * Принимает необязательный параметр 'name'.
     * Доступен по адресу: http://localhost:8080/api/hello
     * Или с параметром: http://localhost:8080/api/hello?name=Alex
     */
    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    /**
     * Возвращает текущее серверное время.
     * Доступен по адресу: http://localhost:8080/api/time
     */
    @GetMapping("/time")
    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Current server time is: " + now.format(formatter);
    }

    /**
     * Трансформирует переданный текст.
     * Принимает параметры 'text' и 'operation' (uppercase, lowercase, reverse).
     * Доступен по адресу: http://localhost:8080/api/text-transform?text=Example&operation=uppercase
     */
    @GetMapping("/text-transform")
    public ResponseEntity<String> transformText(
            @RequestParam String text,
            @RequestParam String operation) {

        switch (operation.toLowerCase()) {
            case "uppercase":
                return ResponseEntity.ok(text.toUpperCase());
            case "lowercase":
                return ResponseEntity.ok(text.toLowerCase());
            case "reverse":
                return ResponseEntity.ok(new StringBuilder(text).reverse().toString());
            default:
                // Возвращаем ошибку 400 Bad Request, если операция некорректна
                return ResponseEntity.badRequest().body("Unknown operation: " + operation);
        }
    }

    /**
     * Генерирует последовательность чисел от 1 до указанного лимита.
     * {limit} - это переменная в пути URL.
     * Доступен по адресу: http://localhost:8080/api/sequence/10
     */
    @GetMapping("/sequence/{limit}")
    public List<Integer> getSequence(@PathVariable int limit) {
        // Генерируем поток чисел от 1 до limit включительно и собираем в список
        return IntStream.rangeClosed(1, limit)
                .boxed()
                .collect(Collectors.toList());
    }
}