package com.goorm.dapum.domain.comment.service;

import com.goorm.dapum.domain.comment.dto.CommentRequest;
import com.goorm.dapum.domain.comment.dto.CommentResponse;
import com.goorm.dapum.domain.comment.entity.Comment;
import com.goorm.dapum.domain.comment.repository.CommentRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostRepository postRepository;

    // 댓글 생성
    public void createComment(Long postId, CommentRequest request) {
        Member member = memberService.findMember();
        Post post = postRepository.findById(postId).get();
        commentRepository.save(new Comment(member, post, request));
    }

    // 특정 게시물의 댓글 불러오기
    public List<CommentResponse> getComments(Long postId) {
        // 게시물에 대한 모든 댓글 조회
        List<Comment> comments = commentRepository.findByPostId(postId);

        // 댓글 엔티티를 DTO로 변환
        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getMember().getId(),
                        comment.getMember().getNickname(),
                        comment.getMember().getProfileImageUrl(),
                        comment.getContent(),
                        comment.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public void updateComment(Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.update(request);
        commentRepository.save(comment);
    }

    // 특정 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(checkAuthority(comment.getMember().getId())){
            commentRepository.delete(comment);
        }
    }

    private boolean checkAuthority(Long actorId){
        Member member = memberService.findMember();
        return member.getId().equals(actorId);
    }

    public long getCommentsCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return commentRepository.countByPost(post);
    }

}
