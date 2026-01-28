package com.example.aiservice.web;

import com.example.aiservice.service.ServiceDirectoryService;
import com.example.aiservice.service.dto.ServiceDetailDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ServiceWebController {
  private final ServiceDirectoryService serviceDirectoryService;

  public ServiceWebController(ServiceDirectoryService serviceDirectoryService) {
    this.serviceDirectoryService = serviceDirectoryService;
  }

  @GetMapping("/services/{slug}")
  public String serviceDetail(@PathVariable String slug, Model model) {
    ServiceDetailDto service = serviceDirectoryService.getServiceBySlug(slug);
    model.addAttribute("service", service);
    return "service-detail";
  }
}
