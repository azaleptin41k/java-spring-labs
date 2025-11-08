package com.labs.lab1.model;

import jakarta.persistence.*; // Важно: импорты из jakarta.persistence
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Генерирует геттеры, сеттеры, toString(), equals() и hashCode()
@NoArgsConstructor // Lombok-аннотация для создания конструктора без аргументов, который нужен JPA
@Entity // Обозначает, что этот класс является сущностью JPA
@Table(name = "teams") // Указывает, что сущность будет храниться в таблице "teams"
public class Team {

    @Id // Обозначает перввичный ключ (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Указывает, что ID генерируется базой данных (автоинкремент)
    private Long id;

    @Column(nullable = false, unique = true) // Поле не может быть null и должно быть уникальным
    private String name;

    @Column(nullable = false) // Поле не может быть null
    private String city;
}