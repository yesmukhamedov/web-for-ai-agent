package com.example.aiservice.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "services")
public class ServiceEntity {
  @Id
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String slug;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 500)
  private String summary;

  @Column(nullable = false, length = 4000)
  private String description;

  @Column(nullable = false)
  private String authorityName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ServiceStatus status;

  private LocalDate validFrom;

  private LocalDate validTo;

  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<RequirementEntity> requirements = new ArrayList<>();

  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<DocumentEntity> documents = new ArrayList<>();

  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<FeeEntity> fees = new ArrayList<>();

  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<TimeframeEntity> timeframes = new ArrayList<>();

  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<LegalSourceEntity> legalSources = new ArrayList<>();

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

  public List<RequirementEntity> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<RequirementEntity> requirements) {
    this.requirements = requirements;
  }

  public List<DocumentEntity> getDocuments() {
    return documents;
  }

  public void setDocuments(List<DocumentEntity> documents) {
    this.documents = documents;
  }

  public List<FeeEntity> getFees() {
    return fees;
  }

  public void setFees(List<FeeEntity> fees) {
    this.fees = fees;
  }

  public List<TimeframeEntity> getTimeframes() {
    return timeframes;
  }

  public void setTimeframes(List<TimeframeEntity> timeframes) {
    this.timeframes = timeframes;
  }

  public List<LegalSourceEntity> getLegalSources() {
    return legalSources;
  }

  public void setLegalSources(List<LegalSourceEntity> legalSources) {
    this.legalSources = legalSources;
  }
}
