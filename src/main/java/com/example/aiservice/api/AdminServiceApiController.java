package com.example.aiservice.api;

import com.example.aiservice.service.ServiceDirectoryService;
import com.example.aiservice.service.dto.ServiceDetailDto;
import com.example.aiservice.service.dto.ServiceRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/services")
public class AdminServiceApiController {
  private final ServiceDirectoryService serviceDirectoryService;

  public AdminServiceApiController(ServiceDirectoryService serviceDirectoryService) {
    this.serviceDirectoryService = serviceDirectoryService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ServiceDetailDto create(@Valid @RequestBody ServiceRequest request) {
    return serviceDirectoryService.createService(request);
  }

  @PutMapping("/{id}")
  public ServiceDetailDto update(@PathVariable UUID id, @Valid @RequestBody ServiceRequest request) {
    return serviceDirectoryService.updateService(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    serviceDirectoryService.deleteService(id);
  }
}
