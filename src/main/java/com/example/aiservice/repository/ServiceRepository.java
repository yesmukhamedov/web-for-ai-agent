package com.example.aiservice.repository;

import com.example.aiservice.domain.ServiceEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID>, JpaSpecificationExecutor<ServiceEntity> {
  Optional<ServiceEntity> findBySlug(String slug);
  boolean existsBySlug(String slug);
}
