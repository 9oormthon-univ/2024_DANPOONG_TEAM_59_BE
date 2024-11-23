package com.goorm.dapum.domain.careComment.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.careRequest.entity.CareRequest;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class CareComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_request_id", nullable = false)
    private CareRequest careRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}