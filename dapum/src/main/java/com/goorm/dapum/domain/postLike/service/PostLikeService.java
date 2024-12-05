package com.goorm.dapum.domain.postLike.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import com.goorm.dapum.domain.postLike.entity.PostLike;
import com.goorm.dapum.domain.postLike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberService memberService;

    public long getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return postLikeRepository.countByPostAndStatus(post, true); // true: 좋아요 개수만 카운트
    }

    @Transactional
    public void toggleLike(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Member member = memberService.findMember();

        PostLike existingLike = (PostLike) postLikeRepository.findByPostAndMember(post, member)
                .orElse(null);

        if (existingLike != null) {
            // 기존 기록이 있으면 상태를 반전시킴
            existingLike.updateStatus(!existingLike.getStatus());
        } else {
            // 기존 기록이 없으면 새로 추가
            PostLike newLike = new PostLike(post, member, true);
            postLikeRepository.save(newLike);
        }
    }

    public boolean isLiked(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Member member = memberService.findMember();

        PostLike existingLike = postLikeRepository.findByPostAndMember(post, member)
                .orElse(null);

        // 좋아요 상태가 true이면 true 반환, 아니면 false 반환
        return existingLike != null && existingLike.getStatus();
    }
}
