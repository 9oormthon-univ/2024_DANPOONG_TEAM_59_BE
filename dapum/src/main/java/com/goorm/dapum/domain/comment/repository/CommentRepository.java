package com.goorm.dapum.domain.comment.repository;

import com.goorm.dapum.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
