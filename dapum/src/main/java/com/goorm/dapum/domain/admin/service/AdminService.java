package com.goorm.dapum.domain.admin.service;

import com.goorm.dapum.domain.PostReport.entity.PostReport;
import com.goorm.dapum.domain.PostReport.repository.PostReportRepository;
import com.goorm.dapum.domain.admin.dto.CareReportList;
import com.goorm.dapum.domain.admin.dto.CareReportRequest;
import com.goorm.dapum.domain.admin.dto.PostReportList;
import com.goorm.dapum.domain.admin.dto.PostReportRequest;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import com.goorm.dapum.domain.carePostReport.entity.CareReport;
import com.goorm.dapum.domain.carePostReport.repository.CareReportRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.dto.PostListResponse;
import com.goorm.dapum.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    @Autowired
    private final PostReportRepository postReportRepository;

    @Autowired
    private final CareReportRepository careReportRepository;

    @Autowired
    private final PostService postService;

    @Autowired
    private final CarePostService carePostService;

    // 게시글 신고 목록 가져오기
    public List<PostReportList> getPostReportList() {
        return postReportRepository.findAll().stream()
                .map(report -> {
                    // PostReport -> PostListResponse 변환
                    var postResponse = postService.createPostListResponse(report.getPost());
                    // PostReportList 생성
                    return new PostReportList(
                            report.getId(),                  // 신고 ID
                            postResponse.postId(),           // 게시글 ID
                            postResponse.memberId(),         // 회원 ID
                            postResponse.nickname(),         // 닉네임
                            postResponse.profileImageUrl(),  // 프로필 이미지
                            postResponse.title(),            // 게시글 제목
                            postResponse.content(),          // 게시글 내용
                            postResponse.imageUrls(),        // 게시글 이미지
                            postResponse.tags(),             // 게시글 태그
                            postResponse.updatedAt(),        // 게시글 업데이트 시간
                            postResponse.likeCount(),        // 좋아요 수
                            postResponse.commentCount(),     // 댓글 수
                            postResponse.isLiked()           // 좋아요 여부
                    );
                })
                .collect(Collectors.toList());
    }

    // 돌봄 게시글 신고 목록 가져오기
    public List<CareReportList> getCarePostReportList() {
        return careReportRepository.findAll().stream()
                .map(report -> {
                    // CareReport -> CarePostListResponse 변환
                    var carePostResponse = carePostService.createCarePostListResponse(report.getCarePost());
                    // CareReportList 생성
                    return new CareReportList(
                            report.getId(),                          // 신고 ID
                            carePostResponse.carePostId(),            // 돌봄 게시글 ID
                            carePostResponse.memberId(),              // 회원 ID
                            carePostResponse.nickname(),              // 닉네임
                            carePostResponse.kakaoProfileImageUrl(),  // 카카오 프로필 이미지
                            carePostResponse.title(),                 // 돌봄 게시글 제목
                            carePostResponse.careDate(),              // 돌봄 날짜
                            carePostResponse.content(),               // 돌봄 게시글 내용
                            carePostResponse.imageUrls(),             // 돌봄 게시글 이미지
                            carePostResponse.tag(),                   // 돌봄 게시글 태그
                            carePostResponse.isEmergency(),           // 긴급 여부
                            carePostResponse.updatedAt(),             // 게시글 업데이트 시간
                            carePostResponse.likeCount(),             // 좋아요 수
                            carePostResponse.commentCount(),          // 댓글 수
                            carePostResponse.isLiked()                // 좋아요 여부
                    );
                })
                .collect(Collectors.toList());
    }


    // 게시글 신고 처리하기
    public void processPostReports(PostReportRequest request) throws BadRequestException {
        // 신고된 게시글을 찾는다.
        PostReport report = postReportRepository.findById(request.postReportId())
                .orElseThrow(() -> new BadRequestException("해당 신고를 찾을 수 없습니다."));

        // 신고 상태가 '신고처리'일 경우 게시글을 삭제하고 신고를 처리
        if (request.state().equals("신고처리")) {
            // 게시글 삭제
            postService.DeletePost(report.getPost().getId());
            Member member = report.getPost().getMember();
            member.deductTemperature(1.0);
        }
        // 신고 상태가 '신고반려'일 경우 해당 게시글을 신고한 모든 신고를 삭제
        else if (request.state().equals("신고반려")) {
            // 해당 게시글을 신고한 모든 신고 삭제
            postReportRepository.deleteByPostId(report.getPost().getId());
        } else {
            throw new BadRequestException("잘못된 신고 상태입니다.");
        }
    }

    // 돌봄 게시글 신고 처리하기
    public void processCarePostReports(CareReportRequest request) throws BadRequestException {
        CareReport report = careReportRepository.findById(request.careReportId())
                .orElseThrow(() -> new BadRequestException("해당 신고를 찾을 수 없습니다."));

        if(request.state().equals("신고처리")){
            carePostService.deleteCarePost(report.getCarePost().getId());
            Member member = report.getCarePost().getMember();
            member.deductTemperature(1.0);
        }
        else if (request.state().equals("신고반려")) {
            // 해당 게시글을 신고한 모든 신고 삭제
            careReportRepository.deleteByCarePostId(report.getCarePost().getId());
        } else {
            throw new BadRequestException("잘못된 신고 상태입니다.");
        }
    }
}
