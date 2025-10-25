package com.labs.lab1.model;

import lombok.Data;

@Data
public class Player {
    private Long id;
    private String name;
    private String position;
    private Long teamId; // Связь с командой по ID
}