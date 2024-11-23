package com.goorm.dapum.domain.careRequest.repository;

import com.goorm.dapum.domain.careRequest.entity.CareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareRequestRepository extends JpaRepository<CareRequest, Long> {
}
