package com.example.aiservice.api;

import com.example.aiservice.service.ServiceDirectoryService;
import com.example.aiservice.service.dto.JsonLdCollectionDto;
import com.example.aiservice.service.dto.JsonLdContext;
import com.example.aiservice.service.dto.JsonLdServiceDetailDto;
import com.example.aiservice.service.dto.JsonLdServiceSummaryDto;
import com.example.aiservice.service.dto.ServiceDetailDto;
import com.example.aiservice.service.dto.ServiceSummaryDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ServiceApiController {
  private final ServiceDirectoryService serviceDirectoryService;

  public ServiceApiController(ServiceDirectoryService serviceDirectoryService) {
    this.serviceDirectoryService = serviceDirectoryService;
  }

  @GetMapping(value = "/services", produces = {MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
  public Object listServices(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) String authority,
      Pageable pageable,
      HttpServletRequest request
  ) {
    Page<ServiceSummaryDto> page = serviceDirectoryService.listServices(status, q, authority, pageable);
    if (acceptsJsonLd(request)) {
      List<JsonLdServiceSummaryDto> graph = page.stream()
          .map(this::toJsonLdSummary)
          .collect(Collectors.toList());
      return new JsonLdCollectionDto(
          new JsonLdContext(),
          "Collection",
          "/api/v1/services",
          graph,
          page.getNumber(),
          page.getSize(),
          page.getTotalElements(),
          page.getTotalPages()
      );
    }
    return page;
  }

  @GetMapping(value = "/services/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
  public Object getService(@PathVariable String id, HttpServletRequest request) {
    ServiceDetailDto detail = serviceDirectoryService.getService(java.util.UUID.fromString(id));
    if (acceptsJsonLd(request)) {
      return toJsonLdDetail(detail, "/api/v1/services/" + detail.id());
    }
    return detail;
  }

  @GetMapping(value = "/services/slug/{slug}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
  public Object getServiceBySlug(@PathVariable String slug, HttpServletRequest request) {
    ServiceDetailDto detail = serviceDirectoryService.getServiceBySlug(slug);
    if (acceptsJsonLd(request)) {
      return toJsonLdDetail(detail, "/api/v1/services/slug/" + detail.slug());
    }
    return detail;
  }

  private boolean acceptsJsonLd(HttpServletRequest request) {
    String accept = request.getHeader("Accept");
    return accept != null && accept.contains("application/ld+json");
  }

  private JsonLdServiceSummaryDto toJsonLdSummary(ServiceSummaryDto dto) {
    return new JsonLdServiceSummaryDto(
        "GovernmentService",
        "/api/v1/services/" + dto.id(),
        dto.id(),
        dto.slug(),
        dto.title(),
        dto.summary(),
        dto.authorityName(),
        dto.status(),
        dto.validFrom(),
        dto.validTo(),
        dto.currentlyValid()
    );
  }

  private JsonLdServiceDetailDto toJsonLdDetail(ServiceDetailDto dto, String id) {
    return new JsonLdServiceDetailDto(
        new JsonLdContext(),
        "GovernmentService",
        id,
        dto.id(),
        dto.slug(),
        dto.title(),
        dto.summary(),
        dto.description(),
        dto.authorityName(),
        dto.status(),
        dto.validFrom(),
        dto.validTo(),
        dto.currentlyValid(),
        dto.requirements(),
        dto.documents(),
        dto.fees(),
        dto.timeframes(),
        dto.legalSources()
    );
  }
}
