package com.labs.lab1.service;

import com.labs.lab1.model.Venue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VenueService {

    // "База данных" для мест проведения
    private final Map<Long, Venue> venues = new ConcurrentHashMap<>();
    // Генератор ID
    private final AtomicLong counter = new AtomicLong();

    /**
     * Создает новое место проведения.
     * @param venue объект места проведения без ID.
     * @return созданное место проведения с присвоенным ID.
     */
    public Venue createVenue(Venue venue) {
        long id = counter.incrementAndGet();
        venue.setId(id);
        venues.put(id, venue);
        return venue;
    }

    /**
     * Возвращает список всех мест проведения.
     * @return список мест проведения.
     */
    public List<Venue> getAllVenues() {
        return new ArrayList<>(venues.values());
    }

    /**
     * Находит место проведения по его ID.
     * @param id ID места проведения.
     * @return Optional, содержащий место проведения, если оно найдено.
     */
    public Optional<Venue> getVenueById(Long id) {
        return Optional.ofNullable(venues.get(id));
    }

    /**
     * Обновляет данные существующего места проведения.
     * @param id ID места проведения для обновления.
     * @param updatedVenue объект с новыми данными.
     * @return Optional, содержащий обновленное место проведения, если оно существовало.
     */
    public Optional<Venue> updateVenue(Long id, Venue updatedVenue) {
        if (venues.containsKey(id)) {
            updatedVenue.setId(id);
            venues.put(id, updatedVenue);
            return Optional.of(updatedVenue);
        }
        return Optional.empty();
    }

    /**
     * Удаляет место проведения по его ID.
     * @param id ID места проведения для удаления.
     * @return true, если место проведения было удалено, иначе false.
     */
    public boolean deleteVenue(Long id) {
        return venues.remove(id) != null;
    }
}