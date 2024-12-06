package com.goorm.dapum.domain.review.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.entity.ChatRoomTag;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.review.dto.ReviewRequest;
import com.goorm.dapum.domain.review.dto.ReviewResponse;
import com.goorm.dapum.domain.review.entity.Review;
import com.goorm.dapum.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    public void createReview(ReviewRequest request) {
        Member from = memberService.findMember();
        Member to = memberService.findById(request.toMemberId());
        ChatRoom chatRoom = chatRoomRepository.findById(request.chatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방(ID: " + request.chatRoomId() + ")을 찾을 수 없습니다."));

        // 작성자 리뷰 상태 업데이트
        chatRoom.completeReviewForMember(from);

        // 리뷰 생성 및 저장
        Review review = Review.builder()
                .from(from)
                .to(to)
                .chatRoom(chatRoom)
                .rating(request.rating())
                .content(request.content())
                .build();

        reviewRepository.save(review);
    }

    // 내가 남긴 리뷰 가져오기
    public ReviewResponse getMyReview(Long chatRoomId) {
        Member currentMember = memberService.findMember();
        Review myReview = reviewRepository.findByChatRoomIdAndFromId(chatRoomId, currentMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방(ID: " + chatRoomId + ")에서 내가 남긴 리뷰를 찾을 수 없습니다."));

        return mapToReviewResponse(myReview);
    }

    // 다른 사람이 나에게 남긴 리뷰 가져오기
    public ReviewResponse getOtherReview(Long chatRoomId) {
        Member currentMember = memberService.findMember();
        Review otherReview = reviewRepository.findByChatRoomIdAndToId(chatRoomId, currentMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방(ID: " + chatRoomId + ")에서 다른 사람이 남긴 리뷰를 찾을 수 없습니다."));

        return mapToReviewResponse(otherReview);
    }

    // Review 엔티티를 ReviewResponse로 변환
    private ReviewResponse mapToReviewResponse(Review review) {
        ChatRoom chatRoom = review.getChatRoom();

        // 공통 필드 설정
        ReviewResponse.ReviewResponseBuilder responseBuilder = ReviewResponse.builder()
                .reviewId(review.getId())
                .fromNickname(review.getFrom().getNickname())
                .toNickname(review.getTo().getNickname())
                .createdAt(review.getCreatedAt())
                .content(review.getContent());

        // CARE/SHARE 구분 처리
        if (chatRoom.getChatRoomTag() == ChatRoomTag.CARE) {
            CarePost carePost = chatRoom.getCarePost();
            responseBuilder
                    .title(carePost.getTitle())
                    .tag(carePost.getCarePostTag().getDisplayName())
                    .isEmergency(carePost.isEmergency())
                    .careDate(carePost.getCareDate())
                    .startTime(carePost.getStartTime())
                    .endTime(carePost.getEndTime());
        } else if (chatRoom.getChatRoomTag() == ChatRoomTag.SHARE) {
            Post post = chatRoom.getPost();
            responseBuilder
                    .title(post.getTitle())
                    .tag(post.getPostTags().toString())
                    .isEmergency(false)
                    .careDate(null) // SHARE에는 careDate가 없음
                    .startTime(null)
                    .endTime(null);
        }

        return responseBuilder.build();
    }

}
