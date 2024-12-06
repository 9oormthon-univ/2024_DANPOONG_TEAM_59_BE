package com.goorm.dapum.domain.carePost.repository;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
    List<CarePost> findByMemberId(Long id);

    @Query("SELECT c FROM CarePost c WHERE c.member.neighborhood.city = :city")
    List<CarePost> findByCity(@Param("city") String city);
}
