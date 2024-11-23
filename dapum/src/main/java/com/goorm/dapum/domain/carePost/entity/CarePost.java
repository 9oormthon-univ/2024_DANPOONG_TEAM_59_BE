package com.goorm.dapum.domain.carePost.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class CarePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}