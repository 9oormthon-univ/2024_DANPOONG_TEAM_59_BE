package com.goorm.dapum.domain.post.repository;

import com.goorm.dapum.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
