package com.goorm.dapum.domain.post.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.dto.PostRequest;
import com.goorm.dapum.domain.post.dto.PostResponse;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    private final MemberService memberService;

    // 게시물 생성
    public void CreatePost(PostRequest request) {
        Member member = memberService.findMember();
        postRepository.save(new Post(member, request));
    }

    // 특정 게시물 가져오기
    public PostResponse GetPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return new PostResponse(post.getId(), post.getMember().getId(), post.getTitle(), post.getContent(), post.getImageUrls(), post.getKeywords());
    }

    // 모든 게시물 가져오기
    public List<PostResponse> GetAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            PostResponse response = new PostResponse(
                    post.getId(),
                    post.getMember().getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    post.getKeywords()
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

}
