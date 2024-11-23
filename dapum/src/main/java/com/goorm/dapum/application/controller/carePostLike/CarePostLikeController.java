package com.goorm.dapum.application.controller.carePostLike;

import com.goorm.dapum.domain.carePostLike.service.CarePostLikeService;
import com.goorm.dapum.domain.postLike.dto.PostLikeResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carePostLikes")
@RequiredArgsConstructor
public class CarePostLikeController {
    private final CarePostLikeService carePostLikeService;

    @PostMapping("/{carePostId}/likes/toggle")
    @Operation(summary = "좋아요/좋아요 취소")
    public ResponseEntity<PostLikeResponse> toggleLike(@PathVariable Long postId) {
        carePostLikeService.toggleLike(postId);
        long likeCount = carePostLikeService.getLikeCount(postId);
        boolean isLiked = carePostLikeService.isLiked(postId);

        return ResponseEntity.ok().body(new PostLikeResponse(likeCount, isLiked));
    }
}
