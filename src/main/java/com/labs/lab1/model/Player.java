package com.labs.lab1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String position;

    // --- СВЯЗЬ ---
    // Аннотация для связи "Много к Одному" (много игроков в одной команде)
    @ManyToOne(fetch = FetchType.LAZY) // LAZY - загружать команду только при прямом обращении к ней (для производительности)
    @JoinColumn(name = "team_id", nullable = false) // Указывает на колонку-внешний ключ "team_id" в таблице "players"
    @JsonBackReference // Помогает избежать бесконечной рекурсии при сериализации в JSON
    private Team team;
}