package com.labs.lab1.repository;

import com.labs.lab1.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // --- Кастомный метод ---
    // Spring Data JPA умеет генерировать SQL-запросы по названию метода.
    // Это название "findByTeamId" будет автоматически преобразовано в SQL-запрос:
    // SELECT * FROM players WHERE team_id = ?
    // Это очень мощная возможность!
    List<Player> findByTeamId(Long teamId);
}