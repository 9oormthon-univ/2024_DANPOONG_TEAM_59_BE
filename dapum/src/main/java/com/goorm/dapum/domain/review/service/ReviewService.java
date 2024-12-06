package com.goorm.dapum.domain.review.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.review.dto.ReviewRequest;
import com.goorm.dapum.domain.review.dto.ReviewResponse;
import com.goorm.dapum.domain.review.entity.Review;
import com.goorm.dapum.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final CarePostService carePostService;

    public void createReview(ReviewRequest request) {
        Member from = memberService.findMember();
        Member to = memberService.findById(request.toMemberId());
        CarePost carePost = carePostService.findById(request.carePostId());
        Review review = Review.builder()
                .from(from)
                .to(to)
                .carePost(carePost)
                .rating(request.rating())
                .content(request.content())
                .build();
        reviewRepository.save(review);
    }

    // 내가 남긴 리뷰 가져오기
    public ReviewResponse getMyReview(Long carePostId) {
        Member currentMember = memberService.findMember();
        Review myReview = reviewRepository.findByCarePostIdAndFromId(carePostId, currentMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 남긴 리뷰가 없습니다."));

        return mapToReviewResponse(myReview);
    }

    // 다른 사람이 나에게 남긴 리뷰 가져오기
    public ReviewResponse getOtherReview(Long carePostId) {
        Member currentMember = memberService.findMember();
        Review otherReview = reviewRepository.findByCarePostIdAndToId(carePostId, currentMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 대해 다른 사람이 남긴 리뷰가 없습니다."));

        return mapToReviewResponse(otherReview);
    }

    // Review 엔티티를 ReviewResponse로 변환
    private ReviewResponse mapToReviewResponse(Review review) {
        CarePost carePost = review.getCarePost();
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .fromNickname(review.getFrom().getNickname())
                .toNickname(review.getTo().getNickname())
                .createdAt(review.getCreatedAt())
                .title(carePost.getTitle())
                .tag(carePost.getCarePostTag().getDisplayName())
                .isEmergency(carePost.isEmergency())
                .careDate(carePost.getCareDate())
                .startTime(carePost.getStartTime())
                .endTime(carePost.getEndTime())
                .content(review.getContent())
                .build();
    }

}
