package com.example.aiservice.service.dto;

import com.example.aiservice.domain.ServiceStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceRequest {
  private UUID id;

  @NotBlank
  @Size(max = 150)
  private String slug;

  @NotBlank
  @Size(max = 255)
  private String title;

  @NotBlank
  @Size(max = 500)
  private String summary;

  @NotBlank
  @Size(max = 4000)
  private String description;

  @NotBlank
  @Size(max = 255)
  private String authorityName;

  @NotNull
  private ServiceStatus status;

  private LocalDate validFrom;

  private LocalDate validTo;

  @Valid
  private List<RequirementRequest> requirements = new ArrayList<>();

  @Valid
  private List<DocumentRequest> documents = new ArrayList<>();

  @Valid
  private List<FeeRequest> fees = new ArrayList<>();

  @Valid
  private List<TimeframeRequest> timeframes = new ArrayList<>();

  @Valid
  private List<LegalSourceRequest> legalSources = new ArrayList<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthorityName() {
    return authorityName;
  }

  public void setAuthorityName(String authorityName) {
    this.authorityName = authorityName;
  }

  public ServiceStatus getStatus() {
    return status;
  }

  public void setStatus(ServiceStatus status) {
    this.status = status;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDate validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDate getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDate validTo) {
    this.validTo = validTo;
  }

  public List<RequirementRequest> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<RequirementRequest> requirements) {
    this.requirements = requirements;
  }

  public List<DocumentRequest> getDocuments() {
    return documents;
  }

  public void setDocuments(List<DocumentRequest> documents) {
    this.documents = documents;
  }

  public List<FeeRequest> getFees() {
    return fees;
  }

  public void setFees(List<FeeRequest> fees) {
    this.fees = fees;
  }

  public List<TimeframeRequest> getTimeframes() {
    return timeframes;
  }

  public void setTimeframes(List<TimeframeRequest> timeframes) {
    this.timeframes = timeframes;
  }

  public List<LegalSourceRequest> getLegalSources() {
    return legalSources;
  }

  public void setLegalSources(List<LegalSourceRequest> legalSources) {
    this.legalSources = legalSources;
  }
}
