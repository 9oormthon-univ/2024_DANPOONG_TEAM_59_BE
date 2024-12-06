package com.goorm.dapum.domain.member.service;

import com.goorm.dapum.application.dto.member.NeighborhoodRequest;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.careComment.repository.CareCommentRepository;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.carePostLike.entity.CarePostLike;
import com.goorm.dapum.domain.carePostLike.repository.CarePostLikeRepository;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.entity.TradeState;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
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
import com.goorm.dapum.domain.userPoint.service.UserPointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private CarePostRepository carePostRepository;

    @Autowired
    private CarePostLikeRepository carePostLikeRepository;

    @Autowired
    private final CareCommentRepository careCommentRepository;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    public void create(MemberRequest request) {
        Member member =new Member(request);
        userPointService.createInitialPoint(member);
        memberRepository.save(member);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void updateNickname(Nickname nickname) {
        Member member = findMember();
        member.updateNickname(nickname);
        System.out.println("사용자 이름 " + member.getKakaoName());
        System.out.println("사용자 닉네임 " + member.getNickname());
        System.out.println("입력한 닉네임 " + nickname.nickname());
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
            Long likeCount = getLikeCount(post);
            Long commentCount = getCommentsCount(post);
            boolean liked = isLiked(member, post);

            // PostTag를 List<String>으로 변환
            List<String> tagNames = post.getPostTags().stream()
                    .map(tag -> tag.getDisplayName())
                    .toList();

            PostLikeList response = new PostLikeList(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getNickname(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    tagNames,
                    post.getUpdatedAt(),
                    likeCount,
                    commentCount,
                    liked
            );

            responses.add(response);
        }

        return responses;
    }

    public long getCommentsCount(Post post) {
        return commentRepository.countByPost(post);
    }

    public long getLikeCount(Post post) {
        return postLikeRepository.countByPostAndStatus(post, true); // true: 좋아요 개수만 카운트
    }

    public boolean isLiked(Member member, Post post) {
        PostLike existingLike = postLikeRepository.findByPostAndMember(post, member)
                .orElse(null);

        // 좋아요 상태가 true이면 true 반환, 아니면 false 반환
        return existingLike != null && existingLike.getStatus();
    }

    private boolean careIsLiked(Member member, CarePost carePost) {
        CarePostLike existingLike = carePostLikeRepository.findByCarePostAndMember(carePost, member)
                .orElse(null);

        // 좋아요 상태가 true이면 true 반환, 아니면 false 반환
        return existingLike != null && existingLike.getStatus();
    }

    private Long getCareCommentsCount(CarePost carePost) {
        return careCommentRepository.countByCarePost(carePost);
    }

    private Long getCareLikeCount(CarePost carePost) {
        return carePostLikeRepository.countByCarePostAndStatus(carePost, true); // true: 좋아요 개수만 카운트
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
            Long likeCount = getLikeCount(post);
            Long commentCount = getCommentsCount(post);
            boolean liked = isLiked(member, post);

            // PostTag를 List<String>으로 변환
            List<String> tagNames = post.getPostTags().stream()
                    .map(tag -> tag.getDisplayName())
                    .toList();

            PostListResponse response = new PostListResponse(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getNickname(),
                    post.getMember().getProfileImageUrl(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrls(),
                    tagNames,
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

    // 사용자의 돌봄을 제공한 게시물 가져오기
    public List<CarePostListResponse> getMyCares() {
        // 현재 로그인한 사용자 조회
        Member member = findMember();

        // 사용자가 작성한 게시물만 조회
        List<CarePost> carePosts = carePostRepository.findByMemberId(member.getId());
        List<CarePostListResponse> responses = new ArrayList<>();

        for (CarePost carePost : carePosts) {
            Long likeCount = getCareLikeCount(carePost);
            Long commentCount = getCareCommentsCount(carePost);
            boolean liked = careIsLiked(member, carePost);
            CarePostListResponse response = new CarePostListResponse(
                    carePost.getId(),
                    carePost.getMember().getId(),
                    carePost.getMember().getNickname(),
                    carePost.getMember().getProfileImageUrl(),
                    carePost.getTitle(),
                    carePost.getCareDate(),
                    carePost.getContent(),
                    carePost.getImageUrls(),
                    carePost.getCarePostTag().getDisplayName(),  // Ensure the tag is displayed as a name
                    carePost.isEmergency(),
                    carePost.getUpdatedAt(),
                    likeCount,
                    commentCount,
                    liked
            );
            responses.add(response);
        }

        return responses;
    }

    // 내가 돌봐준 게시글 목록
    public List<CarePostListResponse> getMyTakeCares() {
        // 현재 로그인된 사용자
        Member currentUser = findMember();

        // 거래 완료된 ChatRoom 가져오기
        List<ChatRoom> completedChatRooms = chatRoomRepository.findByTradeStateAndMember(TradeState.TRADE_COMPLETED, currentUser);

        // 돌봄 제공한 게시글 목록 생성
        List<CarePostListResponse> responses = new ArrayList<>();

        for (ChatRoom chatRoom : completedChatRooms) {
            CarePost carePost = chatRoom.getCarePost();

            // CarePost가 존재하고, 현재 사용자가 작성자가 아닐 경우
            if (carePost != null && !carePost.getMember().equals(currentUser)) {
                Long likeCount = getCareLikeCount(carePost);
                Long commentCount = getCareCommentsCount(carePost);
                boolean liked = careIsLiked(currentUser, carePost);

                CarePostListResponse response = new CarePostListResponse(
                        carePost.getId(),
                        carePost.getMember().getId(),
                        carePost.getMember().getNickname(),
                        carePost.getMember().getProfileImageUrl(),
                        carePost.getTitle(),
                        carePost.getCareDate(),
                        carePost.getContent(),
                        carePost.getImageUrls(),
                        carePost.getCarePostTag().getDisplayName(),  // Ensure the tag is displayed as a name
                        carePost.isEmergency(),
                        carePost.getUpdatedAt(),
                        likeCount,
                        commentCount,
                        liked
                );
                responses.add(response);
            }
        }
        return responses;
    }

}
