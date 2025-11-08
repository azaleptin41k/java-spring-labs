package com.labs.lab1.controller;

import com.labs.lab1.model.Player;
import com.labs.lab1.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players") // Базовый URL для всех эндпоинтов, связанных с игроками
public class PlayerController {

    private final PlayerService playerService;

    // Внедряем зависимость PlayerService через конструктор
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * CREATE: Создает нового игрока.
     * HTTP-метод: POST
     * URL: /api/players
     * Тело запроса: JSON с данными игрока (без id)
     */
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.createPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED); // Ответ 201 Created
    }

    /**
     * READ: Получает список всех игроков.
     * HTTP-метод: GET
     * URL: /api/players
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    /**
     * READ: Получает одного игрока по его ID.
     * HTTP-метод: GET
     * URL: /api/players/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok) // Если игрок найден, вернуть его с кодом 200 OK
                .orElse(ResponseEntity.notFound().build()); // Если не найден, вернуть 404 Not Found
    }

    /**
     * UPDATE: Обновляет существующего игрока.
     * HTTP-метод: PUT
     * URL: /api/players/{id}
     * Тело запроса: JSON с обновленными данными игрока
     */
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE: Удаляет игрока по его ID.
     * HTTP-метод: DELETE
     * URL: /api/players/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.deletePlayer(id)) {
            return ResponseEntity.noContent().build(); // Успешное удаление, ответ 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Игрок не найден, ответ 404 Not Found
        }
    }

    @PostMapping("/{playerId}/transfer")
    public ResponseEntity<Player> transferPlayer(@PathVariable Long playerId, @RequestParam Long newTeamId) {
        // @RequestParam извлекает newTeamId из URL (?newTeamId=...)
        return playerService.transferPlayer(playerId, newTeamId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Отвечаем 404, если игрок или команда не найдены
    }

}