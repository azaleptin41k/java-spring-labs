package com.labs.lab1.service;

import com.labs.lab1.model.Player;
import com.labs.lab1.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import com.labs.lab1.model.Team;
import com.labs.lab1.repository.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }
    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.findById(id)
                .map(existingPlayer -> {
                    existingPlayer.setName(updatedPlayer.getName());
                    existingPlayer.setPosition(updatedPlayer.getPosition());
                    existingPlayer.setTeam(updatedPlayer.getTeam()); // Обновляем и связь с командой
                    return playerRepository.save(existingPlayer);
                });
    }

    public boolean deletePlayer(Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional // Обозначаем, что метод должен выполняться в рамках одной транзакции
    public Optional<Player> transferPlayer(Long playerId, Long newTeamId) {
        // Находим игрока, которого хотим перевести
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        // Находим команду, в которую хотим перевести
        Optional<Team> teamOpt = teamRepository.findById(newTeamId);

        // Проверяем, что и игрок, и команда существуют
        if (playerOpt.isPresent() && teamOpt.isPresent()) {
            Player player = playerOpt.get();
            Team newTeam = teamOpt.get();
            player.setTeam(newTeam); // Меняем команду у игрока
            return Optional.of(playerRepository.save(player));
        }

        return Optional.empty(); // Возвращаем пустоту, если кого-то не нашли
    }

}