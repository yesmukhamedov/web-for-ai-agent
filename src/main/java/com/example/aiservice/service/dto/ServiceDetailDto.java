package com.example.aiservice.service.dto;

import com.example.aiservice.domain.ServiceStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ServiceDetailDto(
    UUID id,
    String slug,
    String title,
    String summary,
    String description,
    String authorityName,
    ServiceStatus status,
    LocalDate validFrom,
    LocalDate validTo,
    boolean currentlyValid,
    List<RequirementDto> requirements,
    List<DocumentDto> documents,
    List<FeeDto> fees,
    List<TimeframeDto> timeframes,
    List<LegalSourceDto> legalSources
) {}
