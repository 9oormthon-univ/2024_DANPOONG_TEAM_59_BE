package com.goorm.dapum.domain.Product.dto;

public record CouponList(
        Long couponId,
        Long productId,
        String name,
        int pointCost,
        String barcode,
        boolean used
) {
}
