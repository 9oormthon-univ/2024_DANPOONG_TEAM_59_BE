package com.goorm.dapum.domain.coupon.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.Product.entity.Product;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member owner; // 쿠폰 소유자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 구매한 상품

    @Column(nullable = false)
    private boolean used; // 사용 여부
}
