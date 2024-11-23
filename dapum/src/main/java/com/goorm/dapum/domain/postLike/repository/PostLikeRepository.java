package com.goorm.dapum.domain.postLike.repository;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    long countByPostAndStatus(Post post, boolean b);

    Optional<PostLike> findByPostAndMember(Post post, Member member);

    List<PostLike> findByMemberAndStatus(Member member, boolean b);
}
