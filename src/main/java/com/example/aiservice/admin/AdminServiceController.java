package com.example.aiservice.admin;

import com.example.aiservice.domain.ServiceStatus;
import com.example.aiservice.service.ServiceDirectoryService;
import com.example.aiservice.service.dto.DocumentRequest;
import com.example.aiservice.service.dto.FeeRequest;
import com.example.aiservice.service.dto.LegalSourceRequest;
import com.example.aiservice.service.dto.RequirementRequest;
import com.example.aiservice.service.dto.ServiceDetailDto;
import com.example.aiservice.service.dto.ServiceRequest;
import com.example.aiservice.service.dto.ServiceSummaryDto;
import com.example.aiservice.service.dto.TimeframeRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/services")
public class AdminServiceController {
  private final ServiceDirectoryService serviceDirectoryService;

  public AdminServiceController(ServiceDirectoryService serviceDirectoryService) {
    this.serviceDirectoryService = serviceDirectoryService;
  }

  @GetMapping
  public String list(Model model) {
    Page<ServiceSummaryDto> services = serviceDirectoryService.listServices(
        null,
        null,
        null,
        PageRequest.of(0, 50)
    );
    model.addAttribute("services", services.getContent());
    return "admin/services";
  }

  @GetMapping("/new")
  public String createForm(Model model) {
    ServiceForm form = new ServiceForm();
    form.setStatus(ServiceStatus.DRAFT);
    model.addAttribute("serviceForm", form);
    model.addAttribute("statuses", ServiceStatus.values());
    model.addAttribute("formAction", "/admin/services/new");
    return "admin/service-form";
  }

  @PostMapping("/new")
  public String create(@Valid @ModelAttribute("serviceForm") ServiceForm form, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("statuses", ServiceStatus.values());
      model.addAttribute("formAction", "/admin/services/new");
      return "admin/service-form";
    }
    ServiceRequest request = toRequest(form, null);
    serviceDirectoryService.createService(request);
    return "redirect:/admin/services";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable UUID id, Model model) {
    ServiceDetailDto detail = serviceDirectoryService.getService(id);
    ServiceForm form = toForm(detail);
    model.addAttribute("serviceForm", form);
    model.addAttribute("statuses", ServiceStatus.values());
    model.addAttribute("formAction", "/admin/services/" + id + "/edit");
    return "admin/service-form";
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable UUID id,
                       @Valid @ModelAttribute("serviceForm") ServiceForm form,
                       BindingResult bindingResult,
                       Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("statuses", ServiceStatus.values());
      model.addAttribute("formAction", "/admin/services/" + id + "/edit");
      return "admin/service-form";
    }
    ServiceDetailDto existing = serviceDirectoryService.getService(id);
    ServiceRequest request = toRequest(form, existing);
    serviceDirectoryService.updateService(id, request);
    return "redirect:/admin/services";
  }

  private ServiceForm toForm(ServiceDetailDto detail) {
    ServiceForm form = new ServiceForm();
    form.setId(detail.id());
    form.setSlug(detail.slug());
    form.setTitle(detail.title());
    form.setSummary(detail.summary());
    form.setDescription(detail.description());
    form.setAuthorityName(detail.authorityName());
    form.setStatus(detail.status());
    form.setValidFrom(detail.validFrom());
    form.setValidTo(detail.validTo());
    return form;
  }

  private ServiceRequest toRequest(ServiceForm form, ServiceDetailDto existing) {
    ServiceRequest request = new ServiceRequest();
    request.setId(form.getId());
    request.setSlug(form.getSlug());
    request.setTitle(form.getTitle());
    request.setSummary(form.getSummary());
    request.setDescription(form.getDescription());
    request.setAuthorityName(form.getAuthorityName());
    request.setStatus(form.getStatus());
    request.setValidFrom(form.getValidFrom());
    request.setValidTo(form.getValidTo());

    if (existing != null) {
      List<RequirementRequest> requirements = existing.requirements().stream()
          .map(req -> {
            RequirementRequest requestItem = new RequirementRequest();
            requestItem.setId(req.id());
            requestItem.setTitle(req.title());
            requestItem.setDetails(req.details());
            requestItem.setRequired(req.required());
            requestItem.setOrderIndex(req.orderIndex());
            return requestItem;
          })
          .collect(Collectors.toList());
      request.setRequirements(requirements);

      List<DocumentRequest> documents = existing.documents().stream()
          .map(doc -> {
            DocumentRequest requestItem = new DocumentRequest();
            requestItem.setId(doc.id());
            requestItem.setName(doc.name());
            requestItem.setDescription(doc.description());
            requestItem.setUrl(doc.url());
            return requestItem;
          })
          .collect(Collectors.toList());
      request.setDocuments(documents);

      List<FeeRequest> fees = existing.fees().stream()
          .map(fee -> {
            FeeRequest requestItem = new FeeRequest();
            requestItem.setId(fee.id());
            requestItem.setAmount(fee.amount());
            requestItem.setCurrency(fee.currency());
            requestItem.setNotes(fee.notes());
            return requestItem;
          })
          .collect(Collectors.toList());
      request.setFees(fees);

      List<TimeframeRequest> timeframes = existing.timeframes().stream()
          .map(tf -> {
            TimeframeRequest requestItem = new TimeframeRequest();
            requestItem.setId(tf.id());
            requestItem.setMinDays(tf.minDays());
            requestItem.setMaxDays(tf.maxDays());
            requestItem.setNotes(tf.notes());
            return requestItem;
          })
          .collect(Collectors.toList());
      request.setTimeframes(timeframes);

      List<LegalSourceRequest> legalSources = existing.legalSources().stream()
          .map(ls -> {
            LegalSourceRequest requestItem = new LegalSourceRequest();
            requestItem.setId(ls.id());
            requestItem.setTitle(ls.title());
            requestItem.setUrl(ls.url());
            requestItem.setPublishedDate(ls.publishedDate());
            return requestItem;
          })
          .collect(Collectors.toList());
      request.setLegalSources(legalSources);
    }

    return request;
  }
}
