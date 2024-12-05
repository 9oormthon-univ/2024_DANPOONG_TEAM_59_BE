package com.goorm.dapum.domain.userPoint.repository;

import com.goorm.dapum.domain.userPoint.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}
