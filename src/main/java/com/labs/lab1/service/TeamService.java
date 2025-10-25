package com.labs.lab1.service;

import com.labs.lab1.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeamService {
    // Наша "база данных" в памяти
    private final Map<Long, Team> teams = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(); // Генератор ID

    public Team createTeam(Team team) {
        long id = counter.incrementAndGet();
        team.setId(id);
        teams.put(id, team);
        return team;
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(teams.values());
    }

    public Optional<Team> getTeamById(Long id) {
        return Optional.ofNullable(teams.get(id));
    }

    public Optional<Team> updateTeam(Long id, Team updatedTeam) {
        if (teams.containsKey(id)) {
            updatedTeam.setId(id);
            teams.put(id, updatedTeam);
            return Optional.of(updatedTeam);
        }
        return Optional.empty();
    }

    public boolean deleteTeam(Long id) {
        return teams.remove(id) != null;
    }
}