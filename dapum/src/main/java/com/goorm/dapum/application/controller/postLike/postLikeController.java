package com.goorm.dapum.application.controller.postLike;

import com.goorm.dapum.domain.postLike.dto.PostLikeResponse;
import com.goorm.dapum.domain.postLike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/postLikes")
@RequiredArgsConstructor
public class postLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes/toggle")
    public ResponseEntity<PostLikeResponse> toggleLike(@PathVariable Long postId) {
        postLikeService.toggleLike(postId);
        long likeCount = postLikeService.getLikeCount(postId);
        boolean isLiked = postLikeService.isLiked(postId);

        return ResponseEntity.ok().body(new PostLikeResponse(likeCount, isLiked));
    }
}
