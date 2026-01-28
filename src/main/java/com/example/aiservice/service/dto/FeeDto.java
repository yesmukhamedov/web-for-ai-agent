package com.example.aiservice.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FeeDto(
    UUID id,
    BigDecimal amount,
    String currency,
    String notes
) {}
