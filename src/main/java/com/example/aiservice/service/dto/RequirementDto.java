package com.example.aiservice.service.dto;

import java.util.UUID;

public record RequirementDto(
    UUID id,
    String title,
    String details,
    boolean required,
    int orderIndex
) {}
