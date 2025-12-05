package com.labs.lab1.model;

public enum SessionStatus {
    ACTIVE,  // Сессия активна
    USED,    // Токен использован (нормальная ротация)
    REVOKED  // Токен отозван (попытка взлома или выход)
}