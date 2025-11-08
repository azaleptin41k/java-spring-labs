package com.labs.lab1.repository;

import com.labs.lab1.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Кастомный метод для получения матчей, которые еще не состоялись.
    // Spring поймет, что нужно найти все матчи, у которых поле matchDateTime
    // находится ПОСЛЕ (After) переданной даты.
    // Эквивалентный SQL: SELECT * FROM matches WHERE match_date_time > ?
    List<Match> findByMatchDateTimeAfter(LocalDateTime dateTime);
}