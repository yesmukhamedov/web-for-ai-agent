package com.example.aiservice.service.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonLdContext {
  private final Map<String, Object> values = new LinkedHashMap<>();

  public JsonLdContext() {
    values.put("@vocab", "https://schema.org/");
    values.put("serviceId", "identifier");
    values.put("authorityName", "governmentOrganization");
    values.put("validFrom", "validFrom");
    values.put("validTo", "validThrough");
    values.put("currentlyValid", "https://example.org/terms/currentlyValid");
    values.put("requirements", "https://example.org/terms/requirements");
    values.put("documents", "https://example.org/terms/documents");
    values.put("fees", "https://example.org/terms/fees");
    values.put("timeframes", "https://example.org/terms/timeframes");
    values.put("legalSources", "https://example.org/terms/legalSources");
  }

  @JsonAnyGetter
  public Map<String, Object> getValues() {
    return Collections.unmodifiableMap(values);
  }
}
