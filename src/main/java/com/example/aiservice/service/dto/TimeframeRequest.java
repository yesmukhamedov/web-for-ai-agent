package com.example.aiservice.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class TimeframeRequest {
  private UUID id;

  @NotNull
  private Integer minDays;

  @NotNull
  private Integer maxDays;

  @Size(max = 2000)
  private String notes;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getMinDays() {
    return minDays;
  }

  public void setMinDays(Integer minDays) {
    this.minDays = minDays;
  }

  public Integer getMaxDays() {
    return maxDays;
  }

  public void setMaxDays(Integer maxDays) {
    this.maxDays = maxDays;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
