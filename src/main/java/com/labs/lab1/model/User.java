package com.labs.lab1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users") // Таблица в БД будет называться users
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Здесь будет храниться ЗАШИФРОВАННЫЙ пароль

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}