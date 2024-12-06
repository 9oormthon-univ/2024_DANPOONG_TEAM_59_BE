package com.goorm.dapum.domain.admin.service;

import com.goorm.dapum.domain.PostReport.entity.PostReport;
import com.goorm.dapum.domain.PostReport.repository.PostReportRepository;
import com.goorm.dapum.domain.admin.dto.CareReportRequest;
import com.goorm.dapum.domain.admin.dto.PostReportRequest;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import com.goorm.dapum.domain.carePostReport.entity.CareReport;
import com.goorm.dapum.domain.carePostReport.repository.CareReportRepository;
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
    public List<PostListResponse> getPostReportList() {
        return postReportRepository.findAll().stream()
                .map(report -> postService.createPostListResponse(report.getPost()))
                .collect(Collectors.toList());
    }

    // 돌봄 게시글 신고 목록 가져오기
    public List<CarePostListResponse> getCarePostReportList() {
        return careReportRepository.findAll().stream()
                .map(report -> carePostService.createCarePostListResponse(report.getCarePost()))
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
        }
        else if (request.state().equals("신고반려")) {
            // 해당 게시글을 신고한 모든 신고 삭제
            careReportRepository.deleteByPostId(report.getCarePost().getId());
        } else {
            throw new BadRequestException("잘못된 신고 상태입니다.");
        }
    }
}
