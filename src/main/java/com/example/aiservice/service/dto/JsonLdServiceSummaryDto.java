package com.example.aiservice.service.dto;

import com.example.aiservice.domain.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;

public record JsonLdServiceSummaryDto(
    @JsonProperty("@type") String type,
    @JsonProperty("@id") String id,
    UUID serviceId,
    String slug,
    String title,
    String summary,
    String authorityName,
    ServiceStatus status,
    LocalDate validFrom,
    LocalDate validTo,
    boolean currentlyValid
) {}
