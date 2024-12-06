package com.goorm.dapum.application.controller.admin;

import com.goorm.dapum.domain.admin.dto.CareReportList;
import com.goorm.dapum.domain.admin.dto.CareReportRequest;
import com.goorm.dapum.domain.admin.dto.PostReportList;
import com.goorm.dapum.domain.admin.dto.PostReportRequest;
import com.goorm.dapum.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @GetMapping("/posts")
    @Operation(summary = "게시글 신고 목록 가져오기")
    public List<PostReportList> getPostReportList() {
        return adminService.getPostReportList();
    }

    @GetMapping("/carePosts")
    @Operation(summary = "돌봄 게시글 신고 목록 가져오기")
    public List<CareReportList> getCarePostReportList() {
        return adminService.getCarePostReportList();
    }

    @PutMapping("/posts")
    @Operation(summary = "게시글 신고처리 하기")
    public void processPostReports(@RequestBody PostReportRequest request) throws BadRequestException {
        adminService.processPostReports(request);
    }

    @PutMapping("/carePosts")
    @Operation(summary = "돌봄 신고처리 하기")
    public void processCarePostReports(@RequestBody CareReportRequest request) throws BadRequestException {
        adminService.processCarePostReports(request);
    }
}
