package com.goorm.dapum.domain.carePost.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarePostService {
    @Autowired
    private CarePostRepository carePostRepository;

    public Optional<CarePost> findById(Long id) {
        return carePostRepository.findById(id);
    }
}
