package com.example.aiservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServiceApiIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void listServicesReturnsSeededData() throws Exception {
    mockMvc.perform(get("/api/v1/services"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content[0].title").exists());
  }

  @Test
  void getServiceBySlugReturnsNestedData() throws Exception {
    mockMvc.perform(get("/api/v1/services/slug/business-license"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.requirements").isArray())
        .andExpect(jsonPath("$.documents").isArray())
        .andExpect(jsonPath("$.fees").isArray());
  }
}
