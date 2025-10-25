package com.labs.lab1.service;

import com.labs.lab1.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlayerService {

    // "База данных" для игроков
    private final Map<Long, Player> players = new ConcurrentHashMap<>();
    // Генератор ID
    private final AtomicLong counter = new AtomicLong();

    /**
     * Создает нового игрока.
     * @param player объект игрока без ID.
     * @return созданный игрок с присвоенным ID.
     */
    public Player createPlayer(Player player) {
        long id = counter.incrementAndGet();
        player.setId(id);
        players.put(id, player);
        return player;
    }

    /**
     * Возвращает список всех игроков.
     * @return список игроков.
     */
    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }

    /**
     * Находит игрока по его ID.
     * @param id ID игрока.
     * @return Optional, содержащий игрока, если он найден.
     */
    public Optional<Player> getPlayerById(Long id) {
        return Optional.ofNullable(players.get(id));
    }

    /**
     * Обновляет данные существующего игрока.
     * @param id ID игрока для обновления.
     * @param updatedPlayer объект с новыми данными.
     * @return Optional, содержащий обновленного игрока, если он существовал.
     */
    public Optional<Player> updatePlayer(Long id, Player updatedPlayer) {
        // Проверяем, существует ли игрок с таким ID
        if (players.containsKey(id)) {
            updatedPlayer.setId(id); // Устанавливаем правильный ID
            players.put(id, updatedPlayer);
            return Optional.of(updatedPlayer);
        }
        return Optional.empty(); // Возвращаем пустой Optional, если игрок не найден
    }

    /**
     * Удаляет игрока по его ID.
     * @param id ID игрока для удаления.
     * @return true, если игрок был удален, иначе false.
     */
    public boolean deletePlayer(Long id) {
        return players.remove(id) != null;
    }
}