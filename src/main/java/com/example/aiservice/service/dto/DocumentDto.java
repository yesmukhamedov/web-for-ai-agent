package com.example.aiservice.service.dto;

import java.util.UUID;

public record DocumentDto(
    UUID id,
    String name,
    String description,
    String url
) {}
