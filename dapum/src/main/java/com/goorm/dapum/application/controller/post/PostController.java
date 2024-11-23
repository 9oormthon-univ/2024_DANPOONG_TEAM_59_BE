package com.goorm.dapum.application.controller.post;

import com.goorm.dapum.domain.post.dto.PostRequest;
import com.goorm.dapum.domain.post.dto.PostResponse;
import com.goorm.dapum.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    // 게시물 생성
    @PostMapping
    @Operation(summary = "게시물 생성")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest request) {
        postService.CreatePost(request);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    // 특정 게시물 가져오기
    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시물 가져오기")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.GetPost(postId);
        return ResponseEntity.ok(response); // HTTP 200과 데이터 반환
    }

    // 모든 게시물 가져오기
    @GetMapping
    @Operation(summary = "모든 게시물 가져오기")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> responses = postService.GetAllPosts();
        return ResponseEntity.ok(responses); // HTTP 200과 데이터 반환
    }

    // 게시물 업데이트
    @PutMapping("/{postId}")
    @Operation(summary = "특정 게시물 업데이트")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequest request) throws BadRequestException {
        postService.UpdatePost(postId, request);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    @Operation(summary = "특정 게시물 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) throws BadRequestException {
        postService.DeletePost(postId);
        return ResponseEntity.ok().build(); // HTTP 200 반환
    }
}
