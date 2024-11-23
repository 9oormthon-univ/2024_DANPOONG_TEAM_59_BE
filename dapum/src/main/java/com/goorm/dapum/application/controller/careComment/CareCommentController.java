package com.goorm.dapum.application.controller.careComment;

import com.goorm.dapum.domain.careComment.dto.CareCommentRequest;
import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;
import com.goorm.dapum.domain.careComment.service.CareCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carePosts/{carePostId}/comments")
@RequiredArgsConstructor
public class CareCommentController {

    private final CareCommentService careCommentService;

    // 댓글 생성
    @PostMapping
    @Operation(summary = "케어 게시물의 댓글 생성")
    public ResponseEntity<Void> createComment(
            @PathVariable Long carePostId,
            @RequestBody CareCommentRequest request) {
        careCommentService.createComment(carePostId, request);
        return ResponseEntity.ok().build();
    }

    // 특정 케어 게시물의 댓글 조회
    @GetMapping
    @Operation(summary = "케어 게시물의 댓글 조회")
    public ResponseEntity<List<CareCommentResponse>> getComments(@PathVariable Long carePostId) {
        List<CareCommentResponse> comments = careCommentService.getComments(carePostId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @Operation(summary = "케어 게시물 댓글 수정")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long carePostId, // CarePostId 경로 확인 용도
            @PathVariable Long commentId,
            @RequestBody CareCommentRequest request) {
        careCommentService.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @Operation(summary = "케어 게시물 댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long carePostId, // CarePostId 경로 확인 용도
            @PathVariable Long commentId) {
        careCommentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
