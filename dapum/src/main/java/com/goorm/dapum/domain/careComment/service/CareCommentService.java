package com.goorm.dapum.domain.careComment.service;

import com.goorm.dapum.domain.careComment.dto.CareCommentRequest;
import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;
import com.goorm.dapum.domain.careComment.entity.CareComment;
import com.goorm.dapum.domain.careComment.repository.CareCommentRepository;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CareCommentService {

    @Autowired
    private final CareCommentRepository careCommentRepository;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final CarePostRepository carePostRepository;

    // 댓글 생성
    public void createComment(Long carePostId, CareCommentRequest request) {
        Member member = memberService.findMember();
        CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
        careCommentRepository.save(new CareComment(member, carePost, request));
    }

    // 특정 게시물의 댓글 가져오기
    public List<CareCommentResponse> getComments(Long carePostId) {
        // 게시물에 대한 모든 댓글 조회
        List<CareComment> comments = careCommentRepository.findByCarePostId(carePostId);

        // 댓글 엔티티를 DTO로 변환
        return comments.stream()
                .map(comment -> new CareCommentResponse(
                        comment.getId(),
                        comment.getMember().getId(),
                        comment.getMember().getNickname(),
                        comment.getMember().getProfileImageUrl(),
                        comment.getContent(),
                        comment.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // 댓글 업데이트
    public void updateComment(Long commentId, CareCommentRequest request) {
        CareComment comment = careCommentRepository.findById(commentId).orElse(null);
        comment.update(request);
        careCommentRepository.save(comment);
    }

    // 특정 댓글 삭제
    public void deleteComment(Long commentId) {
        CareComment comment = careCommentRepository.findById(commentId).orElse(null);
        if (checkAuthority(comment.getMember().getId())) {
            careCommentRepository.delete(comment);
        }
    }

    // 권한 체크
    private boolean checkAuthority(Long actorId) {
        Member member = memberService.findMember();
        return member.getId().equals(actorId);
    }

    // 댓글 개수 조회
    public long getCommentsCount(Long carePostId) {
        CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
        return careCommentRepository.countByCarePost(carePost);
    }
}

