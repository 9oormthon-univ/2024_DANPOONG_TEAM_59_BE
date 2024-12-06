package com.goorm.dapum.domain.Product.dto;

public record QuantityRequest(
        Long productId,
        Integer quantity
) {
}
