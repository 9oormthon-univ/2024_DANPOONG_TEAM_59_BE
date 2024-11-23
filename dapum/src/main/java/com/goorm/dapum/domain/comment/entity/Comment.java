package com.goorm.dapum.domain.comment.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.comment.dto.CommentRequest;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public Comment(Member member, Post post, CommentRequest request) {
       this.member = member;
       this.post = post;
       this.content = request.content();
    }

    public Comment() {

    }

    public void update(CommentRequest request) {
        this.content = request.content();
    }
}
