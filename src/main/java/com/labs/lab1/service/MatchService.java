package com.labs.lab1.service;

import com.labs.lab1.model.Match;
import com.labs.lab1.model.Team;
import com.labs.lab1.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getUpcomingMatches() {
        // Получаем текущее время и передаем его в наш кастомный метод репозитория.
        return matchRepository.findByMatchDateTimeAfter(LocalDateTime.now());
    }

    public Match createMatch(Match match) {
        // --- БИЗНЕС-ЛОГИКА: ПРОВЕРКА ---
        // Убеждаемся, что у нас есть полные объекты команд, а не только их ID.
        // Это хорошая практика, чтобы избежать ошибок с несуществующими командами.
        Team team1 = match.getTeam1();
        Team team2 = match.getTeam2();

        if (team1 == null || team1.getId() == null || team2 == null || team2.getId() == null) {
            throw new IllegalArgumentException("Both team1 and team2 must be specified.");
        }

        // Главная проверка: команда не может играть сама с собой.
        if (team1.getId().equals(team2.getId())) {
            throw new IllegalArgumentException("A team cannot play against itself.");
        }

        // Если все проверки пройдены, сохраняем матч в БД.
        return matchRepository.save(match);
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Optional<Match> recordResult(Long matchId, String result) {
        return matchRepository.findById(matchId)
                .map(match -> {
                    match.setResult(result); // Устанавливаем новое значение поля
                    return matchRepository.save(match); // Сохраняем обновленный матч
                });
    }

    public Optional<Match> updateMatch(Long id, Match updatedMatch) {
        return matchRepository.findById(id)
                .map(existingMatch -> {
                    existingMatch.setTeam1(updatedMatch.getTeam1());
                    existingMatch.setTeam2(updatedMatch.getTeam2());
                    existingMatch.setVenue(updatedMatch.getVenue());
                    existingMatch.setMatchDateTime(updatedMatch.getMatchDateTime());
                    existingMatch.setResult(updatedMatch.getResult());
                    return matchRepository.save(existingMatch);
                });
    }

    public boolean deleteMatch(Long id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
            return true;
        }
        return false;
    }
}