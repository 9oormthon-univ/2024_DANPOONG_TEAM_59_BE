package com.goorm.dapum.domain.comment.repository;

import com.goorm.dapum.domain.comment.entity.Comment;
import com.goorm.dapum.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    long countByPost(Post post);
}
