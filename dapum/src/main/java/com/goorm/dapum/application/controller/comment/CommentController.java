package com.goorm.dapum.application.controller.comment;

import com.goorm.dapum.domain.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goorm.dapum.domain.comment.dto.CommentRequest;
import com.goorm.dapum.domain.comment.dto.CommentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 생성
    @PostMapping()
    @Operation(summary = "댓글 생성")
    public ResponseEntity<Void> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        commentService.createComment(postId, request);
        return ResponseEntity.ok().build();
    }

    // 특정 게시물의 댓글 조회
    @GetMapping
    @Operation(summary = "게시글의 댓글 조회")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long postId, // PostId 경로 확인 용도
            @PathVariable Long commentId,
            @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId, // PostId 경로 확인 용도
            @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
