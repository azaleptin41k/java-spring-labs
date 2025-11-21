package com.labs.lab1.service;

import com.labs.lab1.model.Role;
import com.labs.lab1.model.User;
import com.labs.lab1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password, Role role) {
        // 1. Проверка, существует ли пользователь
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Валидация пароля (Задание 6)
        // Длина > 8, наличие цифр и спецсимволов
        if (!isValidPassword(password)) {
            throw new RuntimeException("Password must be at least 8 characters long and contain special characters.");
        }

        // 3. Создание пользователя
        User user = new User();
        user.setUsername(username);
        // ВАЖНО: Пароль храним только в зашифрованном виде
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    private boolean isValidPassword(String password) {
        // Простая проверка: длина >= 8 и наличие хотя бы одного символа не буквы и не цифры
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }
}