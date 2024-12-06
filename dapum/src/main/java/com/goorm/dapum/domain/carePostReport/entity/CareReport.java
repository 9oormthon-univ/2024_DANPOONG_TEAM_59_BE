package com.goorm.dapum.domain.carePostReport.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_post_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id", nullable = false)
    private CarePost carePost;  // 신고 대상 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 신고한 사용자

    @Column(nullable = false)
    private String reason;  // 신고 사유

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareReportState state;  // 신고 상태
}
