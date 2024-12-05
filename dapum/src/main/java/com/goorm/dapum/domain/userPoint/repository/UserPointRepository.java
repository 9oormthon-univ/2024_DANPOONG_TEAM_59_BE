package com.goorm.dapum.domain.userPoint.repository;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.userPoint.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findByMember(Member member);
}
