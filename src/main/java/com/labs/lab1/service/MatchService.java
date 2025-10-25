package com.labs.lab1.service;

import com.labs.lab1.model.Match;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MatchService {

    // "База данных" для матчей
    private final Map<Long, Match> matches = new ConcurrentHashMap<>();
    // Генератор ID
    private final AtomicLong counter = new AtomicLong();

    /**
     * Создает новый матч.
     * В будущем здесь можно добавить логику проверки,
     * не играют ли команды в других матчах в это же время.
     * @param match объект матча без ID.
     * @return созданный матч с присвоенным ID.
     */
    public Match createMatch(Match match) {
        long id = counter.incrementAndGet();
        match.setId(id);
        matches.put(id, match);
        return match;
    }

    /**
     * Возвращает список всех матчей.
     * @return список матчей.
     */
    public List<Match> getAllMatches() {
        return new ArrayList<>(matches.values());
    }

    /**
     * Находит матч по его ID.
     * @param id ID матча.
     * @return Optional, содержащий матч, если он найден.
     */
    public Optional<Match> getMatchById(Long id) {
        return Optional.ofNullable(matches.get(id));
    }

    /**
     * Обновляет данные существующего матча.
     * @param id ID матча для обновления.
     * @param updatedMatch объект с новыми данными.
     * @return Optional, содержащий обновленный матч, если он существовал.
     */
    public Optional<Match> updateMatch(Long id, Match updatedMatch) {
        if (matches.containsKey(id)) {
            updatedMatch.setId(id);
            matches.put(id, updatedMatch);
            return Optional.of(updatedMatch);
        }
        return Optional.empty();
    }

    /**
     * Удаляет матч по его ID.
     * @param id ID матча для удаления.
     * @return true, если матч был удален, иначе false.
     */
    public boolean deleteMatch(Long id) {
        return matches.remove(id) != null;
    }
}