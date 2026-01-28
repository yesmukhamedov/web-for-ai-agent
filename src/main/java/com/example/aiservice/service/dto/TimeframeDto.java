package com.example.aiservice.service.dto;

import java.util.UUID;

public record TimeframeDto(
    UUID id,
    int minDays,
    int maxDays,
    String notes
) {}
