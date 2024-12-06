package com.goorm.dapum.domain.coupon.repository;

import com.goorm.dapum.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByOwnerId(Long id);
}
