package com.goorm.dapum.domain.Product.entity;

import com.goorm.dapum.domain.Product.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name; // 상품 이름

    @Column(nullable = false)
    private int pointCost; // 상품 가격 (포인트)

    private String barcodeUrl;

    private int quantity;

    public Product(ProductRequest request) {
        this.name = request.name();
        this.barcodeUrl = request.barcodeUrl();
        this.quantity = request.quantity();
        this.pointCost = request.pointCost();
    }

    public void update(ProductRequest request) {
        this.name = request.name();
        this.barcodeUrl = request.barcodeUrl();
        this.quantity = request.quantity();
        this.pointCost = request.pointCost();
    }

    public void deductQuantity() {
        this.quantity--;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}

