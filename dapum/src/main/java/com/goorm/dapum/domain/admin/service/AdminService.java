package com.goorm.dapum.domain.admin.service;

import com.goorm.dapum.domain.PostReport.entity.PostReport;
import com.goorm.dapum.domain.PostReport.repository.PostReportRepository;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import com.goorm.dapum.domain.carePostReport.entity.CareReport;
import com.goorm.dapum.domain.carePostReport.repository.CareReportRepository;
import com.goorm.dapum.domain.post.dto.PostListResponse;
import com.goorm.dapum.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<PostReport> reports = postReportRepository.findAll();
        List<PostListResponse> responses = new ArrayList<>();

        for (PostReport report : reports) {
            PostListResponse response = postService.createPostListResponse(report.getPost());
            responses.add(response);
        }
        return responses;
    }

    // 돌봄 게시글 신고 목록 가져오기
    public List<CarePostListResponse> getCarePostReportList() {
        List<CareReport> reports =  careReportRepository.findAll();
        List<CarePostListResponse> responses = new ArrayList<>();

        for (CareReport report : reports) {
            CarePostListResponse response = carePostService.createCarePostListResponse(report.getCarePost());
            responses.add(response);
        }

        return responses;
    }
}
