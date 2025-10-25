package com.labs.lab1.model;

import lombok.Data;

@Data // Lombok: генерирует геттеры, сеттеры, equals, hashCode, toString
public class Team {
    private Long id;
    private String name;
    private String city;
}