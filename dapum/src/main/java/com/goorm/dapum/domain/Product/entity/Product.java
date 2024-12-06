package com.goorm.dapum.domain.Product.entity;

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

    private String barcode;

    private int quantity;
}

