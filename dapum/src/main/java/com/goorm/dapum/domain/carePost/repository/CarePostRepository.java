package com.goorm.dapum.domain.carePost.repository;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
}
