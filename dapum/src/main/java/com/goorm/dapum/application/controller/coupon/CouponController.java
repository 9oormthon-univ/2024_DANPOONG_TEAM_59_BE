package com.goorm.dapum.application.controller.coupon;

import com.goorm.dapum.domain.Product.dto.CouponList;
import com.goorm.dapum.domain.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/purchase/{productId}")
    @Operation(summary = "상품 구매 후 쿠폰 생성")
    public ResponseEntity<Void> purchaseProduct(@PathVariable Long productId) {
        couponService.purchaseProduct(productId);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    @GetMapping
    @Operation(summary = "사용자가 보유한 쿠폰 리스트 가져오기")
    public ResponseEntity<List<CouponList>> getUserCoupons() {
        List<CouponList> couponLists = couponService.getUserCoupons();
        return ResponseEntity.ok(couponLists); // HTTP 200과 쿠폰 리스트 반환
    }

    @GetMapping("/{couponId}")
    @Operation(summary = "특정 쿠폰 확인")
    public ResponseEntity<CouponList> getCoupon(@PathVariable Long couponId) {
        CouponList coupon = couponService.getCoupon(couponId);
        return ResponseEntity.ok(coupon); // HTTP 200과 쿠폰 상세 정보 반환
    }

    @Operation(summary = "쿠폰 사용 처리")
    @PutMapping("/use/{couponId}")
    public ResponseEntity<Void> useCoupon(@PathVariable Long couponId) {
        couponService.useCoupon(couponId);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }
}
