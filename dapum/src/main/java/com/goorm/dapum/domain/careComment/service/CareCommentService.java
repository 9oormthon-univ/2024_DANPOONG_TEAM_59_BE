package com.goorm.dapum.domain.careComment.service;

import com.goorm.dapum.domain.careComment.dto.CareCommentRequest;
import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;
import com.goorm.dapum.domain.careComment.entity.CareComment;
import com.goorm.dapum.domain.careComment.repository.CareCommentRepository;
import com.goorm.dapum.domain.careRequest.entity.CareRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareCommentService {
    @Autowired
    private final CareCommentRepository careCommentRepository;

    public CareCommentService(CareCommentRepository careCommentRepository) {
        this.careCommentRepository = careCommentRepository;
    }

    public List<CareCommentResponse> getCommentsByCareRequestId(Long careRequestId) {
        List<CareComment> comments = careCommentRepository.findByCareRequestId(careRequestId);
    }

    public void saveComment(CareCommentRequest request) {
        CareRequest =
        careCommentRepository.save(new CareComment());
    }

    public void deleteComment(Long commentId) {
        careCommentRepository.deleteById(commentId);
    }
}
