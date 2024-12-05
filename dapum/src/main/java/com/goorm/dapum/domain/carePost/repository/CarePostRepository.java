package com.goorm.dapum.domain.carePost.repository;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
    List<CarePost> findByMemberId(Long id);
}
