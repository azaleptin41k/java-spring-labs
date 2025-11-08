package com.labs.lab1.controller;

import com.labs.lab1.model.Team;
import com.labs.lab1.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.labs.lab1.model.Player;
import com.labs.lab1.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/teams") // Все эндпоинты для команд будут начинаться с /api/teams
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;

    // Внедрение зависимости через конструктор - лучшая практика
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    // CREATE (Создание)
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    // READ (Получение всех)
    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{teamId}/players")
    public List<Player> getPlayersByTeam(@PathVariable Long teamId) {
        // Вызываем метод из сервиса игроков
        return playerService.getPlayersByTeam(teamId);
    }

    // READ (Получение одной по ID)
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok) // Если команда найдена, вернуть 200 OK
                .orElse(ResponseEntity.notFound().build()); // Иначе вернуть 404 Not Found
    }

    // UPDATE (Изменение)
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.updateTeam(id, team)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE (Удаление)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        if (teamService.deleteTeam(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}