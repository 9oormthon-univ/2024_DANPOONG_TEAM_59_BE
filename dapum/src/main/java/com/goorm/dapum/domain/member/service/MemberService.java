package com.goorm.dapum.domain.member.service;

import com.goorm.dapum.application.dto.member.NeighborhoodRequest;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.comment.repository.CommentRepository;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.entity.Neighborhood;
import com.goorm.dapum.domain.member.entity.Status;
import com.goorm.dapum.domain.member.repository.MemberRepository;
import com.goorm.dapum.domain.post.dto.PostListResponse;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import com.goorm.dapum.domain.postLike.dto.PostLikeList;
import com.goorm.dapum.domain.postLike.entity.PostLike;
import com.goorm.dapum.domain.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public void create(MemberRequest request) {
        memberRepository.save(new Member(request));
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void updateNickname(Nickname nickname) {
        Member member = findMember();
        member.updateNickname(nickname);
        memberRepository.save(member);
    }

    public Member findMember() {
        String email = (String) RequestContextHolder.getRequestAttributes().getAttribute("email", RequestAttributes.SCOPE_REQUEST);
        if (email == null) {
            return null;
        }
        return findByEmail(email);
    }

    public void updateNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        Member member = findMember();
        Neighborhood neighborhood = new Neighborhood(neighborhoodRequest);
        member.updateNeighborhood(neighborhood);
        memberRepository.save(member);
    }

    public List<PostLikeList> getPostLikeList() {
        Member member = findMember();

        // 사용자가 좋아요 한 게시글 조회
        List<PostLike> postLikes = postLikeRepository.findByMemberAndStatus(member, true);

        // 좋아요 한 게시글 정보 생성
        List<PostLikeList> responses = new ArrayList<>();

        for (PostLike postLike : postLikes) {
            Post post = postLike.getPost(); // 좋아요 된 게시물
            Long likeCount = getLikeCount(post.getId());
            Long commentCount = getCommentsCount(post.getId());
            boolean liked = isLiked(post.getId());

            PostLikeList response = new PostLikeList(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getNickname(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    post.getTags(),
                    post.getUpdatedAt(),
                    likeCount,
                    commentCount,
                    liked
            );

            responses.add(response);
        }

        return responses;
    }

    public long getCommentsCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return commentRepository.countByPost(post);
    }

    public long getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return postLikeRepository.countByPostAndStatus(post, true); // true: 좋아요 개수만 카운트
    }

    public boolean isLiked(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Member member = findMember();

        PostLike existingLike = postLikeRepository.findByPostAndMember(post, member)
                .orElse(null);

        // 좋아요 상태가 true이면 true 반환, 아니면 false 반환
        return existingLike != null && existingLike.getStatus();
    }

    public Member findById(Long receiverId) {
        return memberRepository.findById(receiverId).orElse(null);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<PostListResponse> getMyPosts() {
        Member member = findMember(); // 현재 로그인된 사용자
        if (member == null) {
            throw new IllegalArgumentException("로그인된 사용자가 없습니다.");
        }

        List<Post> posts = postRepository.findByMember(member); // 사용자가 작성한 게시물 목록 조회
        List<PostListResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            Long likeCount = getLikeCount(post.getId());
            Long commentCount = getCommentsCount(post.getId());
            boolean liked = isLiked(post.getId());

            PostListResponse response = new PostListResponse(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getNickname(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    post.getTags(),
                    post.getUpdatedAt(),
                    likeCount,
                    commentCount,
                    liked
            );

            responses.add(response);
        }

        return responses;
    }

    public void updateStatus(Member member, Status status) {
        member.updateStatus(Status.ACTIVE);
        memberRepository.save(member);
    }
}
