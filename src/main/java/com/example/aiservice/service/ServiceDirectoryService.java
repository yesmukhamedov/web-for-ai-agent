package com.example.aiservice.service;

import com.example.aiservice.domain.DocumentEntity;
import com.example.aiservice.domain.FeeEntity;
import com.example.aiservice.domain.LegalSourceEntity;
import com.example.aiservice.domain.RequirementEntity;
import com.example.aiservice.domain.ServiceEntity;
import com.example.aiservice.domain.ServiceStatus;
import com.example.aiservice.domain.TimeframeEntity;
import com.example.aiservice.repository.ServiceRepository;
import com.example.aiservice.service.dto.DocumentDto;
import com.example.aiservice.service.dto.DocumentRequest;
import com.example.aiservice.service.dto.FeeDto;
import com.example.aiservice.service.dto.FeeRequest;
import com.example.aiservice.service.dto.LegalSourceDto;
import com.example.aiservice.service.dto.LegalSourceRequest;
import com.example.aiservice.service.dto.RequirementDto;
import com.example.aiservice.service.dto.RequirementRequest;
import com.example.aiservice.service.dto.ServiceDetailDto;
import com.example.aiservice.service.dto.ServiceRequest;
import com.example.aiservice.service.dto.ServiceSummaryDto;
import com.example.aiservice.service.dto.TimeframeDto;
import com.example.aiservice.service.dto.TimeframeRequest;
import com.example.aiservice.service.error.ConflictException;
import com.example.aiservice.service.error.NotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class ServiceDirectoryService {
  private final ServiceRepository serviceRepository;

  public ServiceDirectoryService(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  public Page<ServiceSummaryDto> listServices(String status, String q, String authority, Pageable pageable) {
    Specification<ServiceEntity> spec = Specification.where(null);
    if (StringUtils.hasText(status)) {
      ServiceStatus serviceStatus = ServiceStatus.valueOf(status.toUpperCase());
      spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), serviceStatus));
    }
    if (StringUtils.hasText(authority)) {
      String value = "%" + authority.toLowerCase() + "%";
      spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("authorityName")), value));
    }
    if (StringUtils.hasText(q)) {
      String value = "%" + q.toLowerCase() + "%";
      spec = spec.and((root, query, cb) -> cb.or(
          cb.like(cb.lower(root.get("title")), value),
          cb.like(cb.lower(root.get("summary")), value)
      ));
    }
    return serviceRepository.findAll(spec, pageable)
        .map(this::toSummaryDto);
  }

  public ServiceDetailDto getService(UUID id) {
    ServiceEntity entity = serviceRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Service not found"));
    return toDetailDto(entity);
  }

  public ServiceDetailDto getServiceBySlug(String slug) {
    ServiceEntity entity = serviceRepository.findBySlug(slug)
        .orElseThrow(() -> new NotFoundException("Service not found"));
    return toDetailDto(entity);
  }

  public ServiceDetailDto createService(ServiceRequest request) {
    if (serviceRepository.existsBySlug(request.getSlug())) {
      throw new ConflictException("Service slug already exists");
    }
    ServiceEntity entity = new ServiceEntity();
    entity.setId(UUID.randomUUID());
    applyRequest(entity, request);
    return toDetailDto(serviceRepository.save(entity));
  }

  public ServiceDetailDto updateService(UUID id, ServiceRequest request) {
    ServiceEntity entity = serviceRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Service not found"));
    if (!entity.getSlug().equals(request.getSlug()) && serviceRepository.existsBySlug(request.getSlug())) {
      throw new ConflictException("Service slug already exists");
    }
    applyRequest(entity, request);
    return toDetailDto(serviceRepository.save(entity));
  }

  public void deleteService(UUID id) {
    ServiceEntity entity = serviceRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Service not found"));
    serviceRepository.delete(entity);
  }

  private void applyRequest(ServiceEntity entity, ServiceRequest request) {
    entity.setSlug(request.getSlug());
    entity.setTitle(request.getTitle());
    entity.setSummary(request.getSummary());
    entity.setDescription(request.getDescription());
    entity.setAuthorityName(request.getAuthorityName());
    entity.setStatus(request.getStatus());
    entity.setValidFrom(request.getValidFrom());
    entity.setValidTo(request.getValidTo());

    entity.getRequirements().clear();
    for (RequirementRequest requirementRequest : request.getRequirements()) {
      RequirementEntity requirement = new RequirementEntity();
      requirement.setId(requestId(requirementRequest.getId()));
      requirement.setService(entity);
      requirement.setTitle(requirementRequest.getTitle());
      requirement.setDetails(requirementRequest.getDetails());
      requirement.setRequired(Boolean.TRUE.equals(requirementRequest.getRequired()));
      requirement.setOrderIndex(requirementRequest.getOrderIndex());
      entity.getRequirements().add(requirement);
    }

    entity.getDocuments().clear();
    for (DocumentRequest documentRequest : request.getDocuments()) {
      DocumentEntity document = new DocumentEntity();
      document.setId(requestId(documentRequest.getId()));
      document.setService(entity);
      document.setName(documentRequest.getName());
      document.setDescription(documentRequest.getDescription());
      document.setUrl(documentRequest.getUrl());
      entity.getDocuments().add(document);
    }

    entity.getFees().clear();
    for (FeeRequest feeRequest : request.getFees()) {
      FeeEntity fee = new FeeEntity();
      fee.setId(requestId(feeRequest.getId()));
      fee.setService(entity);
      fee.setAmount(feeRequest.getAmount());
      fee.setCurrency(feeRequest.getCurrency());
      fee.setNotes(feeRequest.getNotes());
      entity.getFees().add(fee);
    }

    entity.getTimeframes().clear();
    for (TimeframeRequest timeframeRequest : request.getTimeframes()) {
      TimeframeEntity timeframe = new TimeframeEntity();
      timeframe.setId(requestId(timeframeRequest.getId()));
      timeframe.setService(entity);
      timeframe.setMinDays(timeframeRequest.getMinDays());
      timeframe.setMaxDays(timeframeRequest.getMaxDays());
      timeframe.setNotes(timeframeRequest.getNotes());
      entity.getTimeframes().add(timeframe);
    }

    entity.getLegalSources().clear();
    for (LegalSourceRequest legalSourceRequest : request.getLegalSources()) {
      LegalSourceEntity legalSource = new LegalSourceEntity();
      legalSource.setId(requestId(legalSourceRequest.getId()));
      legalSource.setService(entity);
      legalSource.setTitle(legalSourceRequest.getTitle());
      legalSource.setUrl(legalSourceRequest.getUrl());
      legalSource.setPublishedDate(legalSourceRequest.getPublishedDate());
      entity.getLegalSources().add(legalSource);
    }
  }

  private UUID requestId(UUID id) {
    return id == null ? UUID.randomUUID() : id;
  }

  private ServiceSummaryDto toSummaryDto(ServiceEntity entity) {
    return new ServiceSummaryDto(
        entity.getId(),
        entity.getSlug(),
        entity.getTitle(),
        entity.getSummary(),
        entity.getAuthorityName(),
        entity.getStatus(),
        entity.getValidFrom(),
        entity.getValidTo(),
        isCurrentlyValid(entity.getValidFrom(), entity.getValidTo())
    );
  }

  private ServiceDetailDto toDetailDto(ServiceEntity entity) {
    List<RequirementDto> requirements = entity.getRequirements().stream()
        .sorted(Comparator.comparingInt(RequirementEntity::getOrderIndex))
        .map(req -> new RequirementDto(req.getId(), req.getTitle(), req.getDetails(), req.isRequired(), req.getOrderIndex()))
        .collect(Collectors.toList());

    List<DocumentDto> documents = entity.getDocuments().stream()
        .map(doc -> new DocumentDto(doc.getId(), doc.getName(), doc.getDescription(), doc.getUrl()))
        .collect(Collectors.toList());

    List<FeeDto> fees = entity.getFees().stream()
        .map(fee -> new FeeDto(fee.getId(), fee.getAmount(), fee.getCurrency(), fee.getNotes()))
        .collect(Collectors.toList());

    List<TimeframeDto> timeframes = entity.getTimeframes().stream()
        .map(tf -> new TimeframeDto(tf.getId(), tf.getMinDays(), tf.getMaxDays(), tf.getNotes()))
        .collect(Collectors.toList());

    List<LegalSourceDto> legalSources = entity.getLegalSources().stream()
        .map(ls -> new LegalSourceDto(ls.getId(), ls.getTitle(), ls.getUrl(), ls.getPublishedDate()))
        .collect(Collectors.toList());

    return new ServiceDetailDto(
        entity.getId(),
        entity.getSlug(),
        entity.getTitle(),
        entity.getSummary(),
        entity.getDescription(),
        entity.getAuthorityName(),
        entity.getStatus(),
        entity.getValidFrom(),
        entity.getValidTo(),
        isCurrentlyValid(entity.getValidFrom(), entity.getValidTo()),
        requirements,
        documents,
        fees,
        timeframes,
        legalSources
    );
  }

  private boolean isCurrentlyValid(LocalDate validFrom, LocalDate validTo) {
    LocalDate today = LocalDate.now();
    boolean afterStart = validFrom == null || !today.isBefore(validFrom);
    boolean beforeEnd = validTo == null || !today.isAfter(validTo);
    return afterStart && beforeEnd;
  }
}
