package com.labs.lab1.service;

import com.labs.lab1.model.Team;
import com.labs.lab1.repository.TeamRepository; // Импортируем наш репозиторий
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    // Внедряем зависимость через final поле и конструктор - это лучшая практика.
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // CREATE: Метод save() используется и для создания, и для обновления.
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // READ All:
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // READ by ID: Метод findById() возвращает Optional, чтобы безопасно обработать случай, когда сущность не найдена.
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    // UPDATE:
    public Optional<Team> updateTeam(Long id, Team updatedTeam) {
        // Проверяем, существует ли команда с таким ID
        return teamRepository.findById(id)
                .map(existingTeam -> { // .map() выполнится, только если команда найдена
                    existingTeam.setName(updatedTeam.getName());
                    existingTeam.setCity(updatedTeam.getCity());
                    return teamRepository.save(existingTeam); // Сохраняем обновленную сущность
                });
    }

    // DELETE:
    public boolean deleteTeam(Long id) {
        if (teamRepository.existsById(id)) { // Проверяем существование, чтобы не делать лишний запрос
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}