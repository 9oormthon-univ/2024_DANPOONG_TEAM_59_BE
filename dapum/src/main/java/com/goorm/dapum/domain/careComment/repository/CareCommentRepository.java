package com.goorm.dapum.domain.careComment.repository;

import com.goorm.dapum.domain.careComment.entity.CareComment;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareCommentRepository extends JpaRepository<CareComment, Long> {

    List<CareComment> findByCarePostId(Long carePostId);

    long countByCarePost(CarePost carePost);
}