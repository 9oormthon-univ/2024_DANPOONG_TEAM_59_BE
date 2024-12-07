package com.goorm.dapum.domain.Product.dto;

public record ProductRequest(
        String name,
        int pointCost,
        String barcodeUrl,
        int quantity
) {
}
