package com.goorm.dapum.domain.careComment.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.careComment.dto.CareCommentRequest;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CareComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id", nullable = false)
    private CarePost carePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public CareComment(Member member, CarePost carePost, CareCommentRequest request) {
        this.carePost = carePost;
        this.member = member;
        this.content = request.content();
    }

    public CareComment() {

    }
    public void update(CareCommentRequest request) {
        this.content = request.content();
    }
}