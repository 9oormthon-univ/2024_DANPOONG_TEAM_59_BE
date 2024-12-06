package com.goorm.dapum.domain.carePost.service;

import com.goorm.dapum.domain.carePostReport.dto.CareReportRequest;
import com.goorm.dapum.domain.carePostReport.entity.CareReport;
import com.goorm.dapum.domain.carePostReport.entity.CareReportState;
import com.goorm.dapum.domain.carePostReport.repository.CareReportRepository;
import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;
import com.goorm.dapum.domain.careComment.service.CareCommentService;
import com.goorm.dapum.domain.carePost.dto.CarePostRequest;
import com.goorm.dapum.domain.carePost.dto.CarePostResponse;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.carePostLike.service.CarePostLikeService;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarePostService {

    @Autowired
    private final CarePostRepository carePostRepository;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final CareCommentService careCommentService;

    @Autowired
    private final CarePostLikeService carePostLikeService;
    @Autowired
    private CareReportRepository careReportRepository;

    // 게시물 생성
    public void createCarePost(CarePostRequest request) {
        Member member = memberService.findMember();
        carePostRepository.save(new CarePost(member, request));
    }

    // 특정 게시물 가져오기
    public CarePostResponse getCarePost(Long id) {
        CarePost carePost = carePostRepository.findById(id).orElse(null);
        if (carePost == null) {
            return null;
        }
        List<CareCommentResponse> comments = careCommentService.getComments(carePost.getId());
        Long likeCount = carePostLikeService.getLikeCount(carePost.getId());
        boolean liked = carePostLikeService.isLiked(carePost.getId());

        // 수정된 CarePostResponse 생성
        return new CarePostResponse(
                carePost.getId(),
                carePost.getMember().getId(),
                carePost.getMember().getNickname(),
                carePost.getMember().getProfileImageUrl(),
                carePost.getTitle(),
                carePost.getContent(),
                carePost.getImageUrls(),
                carePost.getCarePostTag().getDisplayName(), // 태그를 표시 이름으로 반환
                carePost.isEmergency(), // 긴급 여부
                carePost.getCareDate(),
                carePost.getStartTime(),
                carePost.getEndTime(),
                carePost.getUpdatedAt(),
                comments,
                likeCount,
                liked,
                carePost.getMember().getNeighborhood().getProvince(),
                carePost.getMember().getNeighborhood().getCity(),
                carePost.getMember().getNeighborhood().getDistrict()
        );
    }

    // 모든 게시물 가져오기
    public List<CarePostListResponse> getAllCarePosts() {
        List<CarePost> carePosts = carePostRepository.findAll();
        List<CarePostListResponse> responses = new ArrayList<>();

        for (CarePost carePost : carePosts) {
            CarePostListResponse response = createCarePostListResponse(carePost);
            responses.add(response);
        }

        return responses;
    }

    public CarePostListResponse createCarePostListResponse(CarePost carePost) {
        Long likeCount = carePostLikeService.getLikeCount(carePost.getId());
        Long commentCount = careCommentService.getCommentsCount(carePost.getId());
        boolean liked = carePostLikeService.isLiked(carePost.getId());

        return new CarePostListResponse(
                carePost.getId(),
                carePost.getMember().getId(),
                carePost.getMember().getNickname(),
                carePost.getMember().getProfileImageUrl(),
                carePost.getTitle(),
                carePost.getCareDate(),
                carePost.getContent(),
                carePost.getImageUrls(),
                carePost.getCarePostTag().getDisplayName(),  // 태그의 이름을 가져오는 부분
                carePost.isEmergency(),
                carePost.getUpdatedAt(),
                likeCount,
                commentCount,
                liked
        );
    }


    // 게시물 업데이트
    public void updateCarePost(Long carePostId, CarePostRequest request) throws BadRequestException {
        Member member = memberService.findMember();
        CarePost carePost = carePostRepository.findById(carePostId)
                .orElseThrow(() -> new BadRequestException("해당 게시물을 찾을 수 없습니다."));

        if (!hasAuthority(member, carePost)) {
            throw new BadRequestException("게시물을 수정할 권한이 없습니다.");
        }

        carePost.update(request);
        carePostRepository.save(carePost);
    }

    // 게시물 삭제
    public void deleteCarePost(Long carePostId) throws BadRequestException {
        Member member = memberService.findMember();
        CarePost carePost = carePostRepository.findById(carePostId)
                .orElseThrow(() -> new BadRequestException("해당 게시물을 찾을 수 없습니다."));

        if (!hasAuthority(member, carePost)) {
            throw new BadRequestException("게시물을 삭제할 권한이 없습니다.");
        }

        carePostRepository.delete(carePost);
    }

    // 권한 확인
    private boolean hasAuthority(Member member, CarePost carePost) {
        return carePost.getMember().getId().equals(member.getId());
    }

    // 게시물 ID로 찾기
    public CarePost findById(Long carePostId) {
        return carePostRepository.findById(carePostId).orElse(null);
    }

    public void reportCarePost(CareReportRequest request) {
        Member member = memberService.findMember();

        CarePost carePost = carePostRepository.findById(request.carePostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        if (careReportRepository.findByCarePostIdAndMemberId(carePost.getId(), member.getId()).isPresent()){
            throw new IllegalArgumentException("이미 신고한 게시글입니다.");
        }

        CareReport careReport = CareReport.builder()
                .carePost(carePost)
                .member(member)
                .reason(request.reason())
                .state(CareReportState.PENDING)
                .build();
        careReportRepository.save(careReport);
    }
}
