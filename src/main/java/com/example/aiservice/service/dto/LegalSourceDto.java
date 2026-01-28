package com.example.aiservice.service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record LegalSourceDto(
    UUID id,
    String title,
    String url,
    LocalDate publishedDate
) {}
