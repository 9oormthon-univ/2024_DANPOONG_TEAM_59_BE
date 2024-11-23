package com.goorm.dapum.domain.memberReview.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class MemberReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_member_id", nullable = false)
    private Member reviewedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Member reviewer;

    @Column(nullable = false)
    private int rating;  // 평점 (1~5 등)

    @Column(columnDefinition = "TEXT")
    private String comment;
}
