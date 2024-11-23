package com.goorm.dapum.domain.carePostLike.repository;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePostLike.entity.CarePostLike;
import com.goorm.dapum.domain.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarePostLikeRepository extends CrudRepository<CarePostLike, Long> {
    long countByCarePostAndStatus(CarePost carePost, boolean status);

    Optional<CarePostLike> findByCarePostAndMember(CarePost carePost, Member member);
}
