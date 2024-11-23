package com.goorm.dapum.domain.careComment.service;

import com.goorm.dapum.domain.careComment.dto.CareCommentRequest;
import com.goorm.dapum.domain.careComment.entity.CareComment;
import com.goorm.dapum.domain.careComment.repository.CareCommentRepository;
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

    public List<CareComment> getCommentsByCareRequestId(Long carePostId) {
        return careCommentRepository.findByCarePostId(carePostId);
    }

    public void saveComment(CareCommentRequest request) {
        careCommentRepository.save(new CareComment());
    }

    public void deleteComment(Long commentId) {
        careCommentRepository.deleteById(commentId);
    }
}
