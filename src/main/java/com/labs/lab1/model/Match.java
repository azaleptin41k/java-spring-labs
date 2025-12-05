package com.labs.lab1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- СВЯЗИ ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team1_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team team1; // Первая команда-участник

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team2_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team team2; // Вторая команда-участник

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Venue venue; // Место проведения

    // --- ОБЫЧНЫЕ ПОЛЯ ---
    @Column(name = "match_date_time", nullable = false)
    private LocalDateTime matchDateTime; // Дата и время матча

    private String result; // Например, "2-1"
}