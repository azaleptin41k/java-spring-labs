package com.labs.lab1.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Match {
    private Long id;
    private Long team1Id;
    private Long team2Id;
    private Long venueId;
    private LocalDateTime matchDateTime;
    private String result; // Например, "2-1"
}