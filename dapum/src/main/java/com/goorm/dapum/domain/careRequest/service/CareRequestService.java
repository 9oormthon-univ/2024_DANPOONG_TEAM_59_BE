package com.goorm.dapum.domain.careRequest.service;

import com.goorm.dapum.domain.careRequest.entity.CareRequest;
import com.goorm.dapum.domain.careRequest.repository.CareRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CareRequestService {
    @Autowired
    private CareRequestRepository careRequestRepository;

    public Optional<CareRequest> findById(Long id) {
        return careRequestRepository.findById(id);
    }
}
