package com.goorm.dapum.domain.coupon.service;

import com.goorm.dapum.domain.Product.dto.CouponList;
import com.goorm.dapum.domain.Product.entity.Product;
import com.goorm.dapum.domain.Product.repository.ProductRepository;
import com.goorm.dapum.domain.coupon.entity.Coupon;
import com.goorm.dapum.domain.coupon.repository.CouponRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {
    @Autowired
    private final CouponRepository couponRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final MemberService memberService;

    // 상품 구매 후 쿠폰 생성
    public void purchaseProduct(Long productId) {
        Member member = memberService.findMember();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 포인트 확인 및 차감
        member.deductPoints(product.getPointCost());
        product.deductQuantity();

        // 쿠폰 생성
        Coupon coupon = Coupon.builder()
                .owner(member)
                .product(product)
                .used(false)
                .build();

        couponRepository.save(coupon);
    }

    // 사용자가 보유한 쿠폰 리스트 가져오기
    public List<CouponList> getUserCoupons() {
        Member member = memberService.findMember(); // 현재 로그인한 사용자 정보
        List<Coupon> coupons = couponRepository.findByOwnerId(member.getId()); // 사용자의 쿠폰 리스트 조회

        // Coupon 엔티티를 CouponList DTO로 변환
        return coupons.stream()
                .map(coupon -> new CouponList(
                        coupon.getId(),
                        coupon.getProduct().getId(),
                        coupon.getProduct().getName(),
                        coupon.getProduct().getPointCost(),
                        coupon.getProduct().getBarcodeUrl(),
                        coupon.isUsed()
                ))
                .toList();
    }

    // 특정 쿠폰 확인
    public CouponList getCoupon(Long couponId) {
        Member member = memberService.findMember();
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        Product product = coupon.getProduct();
        return new CouponList(coupon.getId(),product.getId(), product.getName(), product.getPointCost(), product.getBarcodeUrl(), coupon.isUsed());
    }

    // 쿠폰 사용 처리
    public void useCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        if (coupon.isUsed()) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
        coupon.setUsed(true); // 사용 처리
        couponRepository.save(coupon);
    }
}
