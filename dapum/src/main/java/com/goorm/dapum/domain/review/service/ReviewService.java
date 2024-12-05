package com.goorm.dapum.domain.review.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.review.dto.ReviewRequest;
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

}
