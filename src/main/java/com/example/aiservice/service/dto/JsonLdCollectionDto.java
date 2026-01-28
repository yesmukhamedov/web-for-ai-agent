package com.example.aiservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record JsonLdCollectionDto(
    @JsonProperty("@context") JsonLdContext context,
    @JsonProperty("@type") String type,
    @JsonProperty("@id") String id,
    @JsonProperty("@graph") List<JsonLdServiceSummaryDto> graph,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
