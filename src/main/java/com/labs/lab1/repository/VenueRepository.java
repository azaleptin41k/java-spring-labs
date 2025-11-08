package com.labs.lab1.repository;

import com.labs.lab1.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // Здесь также пусто, JpaRepository предоставляет все необходимое.
}