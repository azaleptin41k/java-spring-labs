package com.labs.lab1.repository;

import com.labs.lab1.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    Optional<UserSession> findByRefreshToken(String refreshToken);
    Optional<UserSession> findByAccessToken(String accessToken);
    List<UserSession> findAllByUserEmail(String userEmail);
}