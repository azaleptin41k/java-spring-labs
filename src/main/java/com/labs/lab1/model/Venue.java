package com.labs.lab1.model;

import lombok.Data;

@Data
public class Venue {
    private Long id;
    private String name;
    private String city;
    private int capacity;
}