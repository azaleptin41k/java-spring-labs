package com.labs.lab1.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String userEmail; // В нашем случае это username

    private String deviceId; // Можно пока оставлять null или передавать из клиента

    @Column(length = 2048) // Токены могут быть длинными
    private String accessToken;

    @Column(length = 2048)
    private String refreshToken;

    private Instant accessTokenExpiry;
    private Instant refreshTokenExpiry;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;
}