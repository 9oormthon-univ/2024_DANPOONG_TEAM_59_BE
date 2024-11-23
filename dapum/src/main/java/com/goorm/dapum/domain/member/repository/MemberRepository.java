package com.goorm.dapum.domain.member.repository;

import com.goorm.dapum.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Member findByEmail(String email);
}
