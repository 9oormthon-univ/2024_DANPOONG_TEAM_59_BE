package com.goorm.dapum.application.controller.review;

import com.goorm.dapum.domain.review.dto.ReviewRequest;
import com.goorm.dapum.domain.review.dto.ReviewResponse;
import com.goorm.dapum.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 생성")
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my/{carePostId}")
    @Operation(summary = "내가 남긴 리뷰 조회")
    public ResponseEntity<ReviewResponse> getMyReview(@PathVariable Long carePostId) {
        ReviewResponse response = reviewService.getMyReview(carePostId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other/{carePostId}")
    @Operation(summary = "다른 사람이 나에게 남긴 리뷰 조회")
    public ResponseEntity<ReviewResponse> getOtherReview(@PathVariable Long carePostId) {
        ReviewResponse response = reviewService.getOtherReview(carePostId);
        return ResponseEntity.ok(response);
    }
}
