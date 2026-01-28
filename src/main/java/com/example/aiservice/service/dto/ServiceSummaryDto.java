package com.example.aiservice.service.dto;

import com.example.aiservice.domain.ServiceStatus;
import java.time.LocalDate;
import java.util.UUID;

public record ServiceSummaryDto(
    UUID id,
    String slug,
    String title,
    String summary,
    String authorityName,
    ServiceStatus status,
    LocalDate validFrom,
    LocalDate validTo,
    boolean currentlyValid
) {}
