package com.goorm.dapum.domain.post.repository;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member member);
}
