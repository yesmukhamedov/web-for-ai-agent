package com.example.aiservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Government Services Knowledge API",
        description = "Machine-readable knowledge API for public services.",
        version = "v1"
    )
)
public class OpenApiConfig {
}
