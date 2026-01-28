package com.example.aiservice.web;

import com.example.aiservice.service.ServiceDirectoryService;
import com.example.aiservice.service.dto.ServiceSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
  private final ServiceDirectoryService serviceDirectoryService;

  public HomeController(ServiceDirectoryService serviceDirectoryService) {
    this.serviceDirectoryService = serviceDirectoryService;
  }

  @GetMapping("/")
  public String home(@RequestParam(required = false) String q, Model model) {
    Page<ServiceSummaryDto> services = serviceDirectoryService.listServices(
        "ACTIVE",
        q,
        null,
        PageRequest.of(0, 20)
    );
    model.addAttribute("services", services.getContent());
    model.addAttribute("query", q == null ? "" : q);
    return "index";
  }
}
