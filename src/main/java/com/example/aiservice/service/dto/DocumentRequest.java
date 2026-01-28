package com.example.aiservice.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class DocumentRequest {
  private UUID id;

  @NotBlank
  @Size(max = 255)
  private String name;

  @NotBlank
  @Size(max = 2000)
  private String description;

  @NotBlank
  @Size(max = 500)
  private String url;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
