package com.goorm.dapum.application.controller.carePost;

import com.goorm.dapum.domain.carePostReport.dto.CareReportRequest;
import com.goorm.dapum.domain.carePost.dto.CarePostRequest;
import com.goorm.dapum.domain.carePost.dto.CarePostResponse;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.service.CarePostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carePosts")
@RequiredArgsConstructor
public class CarePostController {
    private final CarePostService carePostService;

    // 케어 게시물 생성
    @PostMapping
    @Operation(summary = "케어 게시물 생성")
    public ResponseEntity<Void> createCarePost(@RequestBody CarePostRequest request) {
        carePostService.createCarePost(request);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    // 특정 케어 게시물 가져오기
    @GetMapping("/{carePostId}")
    @Operation(summary = "특정 케어 게시물 가져오기")
    public ResponseEntity<CarePostResponse> getCarePost(@PathVariable Long carePostId) {
        CarePostResponse response = carePostService.getCarePost(carePostId);
        return ResponseEntity.ok(response); // HTTP 200과 데이터 반환
    }

    // 모든 케어 게시물 가져오기
    @GetMapping
    @Operation(summary = "모든 케어 게시물 가져오기")
    public ResponseEntity<List<CarePostListResponse>> getAllCarePosts() {
        List<CarePostListResponse> responses = carePostService.getAllCarePosts();
        return ResponseEntity.ok(responses); // HTTP 200과 데이터 반환
    }

    // 케어 게시물 업데이트
    @PutMapping("/{carePostId}")
    @Operation(summary = "특정 케어 게시물 업데이트")
    public ResponseEntity<Void> updateCarePost(@PathVariable Long carePostId, @RequestBody CarePostRequest request) throws BadRequestException {
        carePostService.updateCarePost(carePostId, request);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    // 케어 게시물 삭제
    @DeleteMapping("/{carePostId}")
    @Operation(summary = "특정 케어 게시물 삭제")
    public ResponseEntity<Void> deleteCarePost(@PathVariable Long carePostId) throws BadRequestException {
        carePostService.deleteCarePost(carePostId);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    // 게시물 신고
    @PostMapping("/report")
    @Operation(summary = "게시물 신고")
    public ResponseEntity<Void> reportPost(@RequestBody CareReportRequest request) {
        carePostService.reportCarePost(request);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }
}
