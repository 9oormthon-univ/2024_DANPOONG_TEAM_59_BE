package com.goorm.dapum.domain.review.entity;

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
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id", nullable = false)
    private Member from; // 돌봄을 요청한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id", nullable = false)
    private Member to; // 돌봄을 제공한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id", nullable = false)
    private CarePost carePost; // 돌봄 게시글

    @Column(nullable = false)
    private int rating; // 별점 (1~5)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 후기 내용
}
