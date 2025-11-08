package com.labs.lab1.repository;

import com.labs.lab1.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Аннотация @Repository говорит Spring, что это компонент для доступа к данным
// и позволяет перехватывать специфичные для БД исключения.
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // JpaRepository<Team, Long> означает:
    // 1. Team - это сущность, с которой мы работаем.
    // 2. Long - это тип первичного ключа (@Id) этой сущности.

    // Здесь пусто! Spring Data JPA предоставит нам все основные CRUD-методы
    // (save, findById, findAll, deleteById, existsById, count и т.д.) автоматически.
}