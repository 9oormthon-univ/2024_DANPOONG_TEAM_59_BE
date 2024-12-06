package com.goorm.dapum.domain.post.service;

import com.goorm.dapum.domain.PostReport.dto.PostReportRequest;
import com.goorm.dapum.domain.PostReport.entity.PostReport;
import com.goorm.dapum.domain.PostReport.repository.PostReportRepository;
import com.goorm.dapum.domain.comment.dto.CommentResponse;
import com.goorm.dapum.domain.comment.service.CommentService;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.dto.PostRequest;
import com.goorm.dapum.domain.post.dto.PostResponse;
import com.goorm.dapum.domain.post.dto.PostListResponse;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import com.goorm.dapum.domain.postLike.service.PostLikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final CommentService commentService;

    @Autowired
    private final PostLikeService postLikeService;

    @Autowired
    private final PostReportRepository postReportRepository;

    // 게시물 생성
    public void CreatePost(PostRequest request) {
        Member member = memberService.findMember();
        postRepository.save(new Post(member, request));
    }

    // 특정 게시물 가져오기
    public PostResponse GetPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        List<CommentResponse> comments = commentService.getComments(post.getId());
        Long likeCount = postLikeService.getLikeCount(post.getId());
        boolean liked = postLikeService.isLiked(post.getId());
        return new PostResponse(
                post.getId(),
                post.getMember().getId(),
                post.getMember().getNickname(),
                post.getMember().getProfileImageUrl(),
                post.getTitle(), post.getContent(),
                post.getImageUrls(), post.getPostTags(),
                post.getUpdatedAt(),
                comments,
                likeCount,
                liked);
    }

    // 모든 게시물 가져오기
    public List<PostListResponse> GetAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostListResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            Long likeCount = postLikeService.getLikeCount(post.getId());
            Long commentCount = commentService.getCommentsCount(post.getId());
            boolean liked = postLikeService.isLiked(post.getId());
            PostListResponse response = new PostListResponse(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getNickname(),
                    post.getMember().getProfileImageUrl(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    post.getPostTags(),
                    post.getUpdatedAt(),
                    likeCount,
                    commentCount,
                    liked
            );
            responses.add(response);
        }

        return responses;
    }

    // 게시물 업데이트
    public void UpdatePost(Long postId, PostRequest request) throws BadRequestException {
        Member member = memberService.findMember(); // 현재 로그인된 사용자 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException("해당 게시물을 찾을 수 없습니다."));

        // 작성자 확인
        if (!authority(member, post)) {
            throw new BadRequestException("게시물을 수정할 권한이 없습니다.");
        }

        // 게시물 내용 업데이트
        post.update(request);

        // 변경 사항 저장
        postRepository.save(post);
    }

    public void DeletePost(Long postId) throws BadRequestException {
        Member member = memberService.findMember(); // 현재 로그인된 사용자 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException("해당 게시물을 찾을 수 없습니다."));

        // 작성자 확인
        if (!authority(member, post)) {
            throw new BadRequestException("게시물을 수정할 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    private boolean authority(Member member, Post post) {
        if (!post.getMember().getId().equals(member.getId())) {
            return false;
        }
        return true;
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    // 게시물 신고
    public void reportPost(PostReportRequest request) {
        Member member = memberService.findMember(); // 현재 로그인한 사용자

        // 신고 대상 게시글 확인
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        // 중복 신고 방지
        if (postReportRepository.findByPostIdAndMemberId(post.getId(), member.getId()).isPresent()) {
            throw new IllegalArgumentException("이미 신고한 게시글입니다.");
        }

        // 신고 저장
        PostReport report = PostReport.builder()
                .post(post)
                .member(member)
                .reason(request.reason())
                .build();
        postReportRepository.save(report);
    }
}

