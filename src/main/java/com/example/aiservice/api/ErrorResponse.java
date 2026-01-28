package com.example.aiservice.api;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    String message,
    Map<String, String> errors,
    Instant timestamp
) {}
