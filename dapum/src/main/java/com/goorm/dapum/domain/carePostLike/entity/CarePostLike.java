package com.goorm.dapum.domain.carePostLike.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CarePostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "care_post_id", nullable = false)
    private CarePost carePost;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean status = true; // true: liked, false: unliked

    public CarePostLike(CarePost carePost, Member member, boolean b) {
        this.carePost = carePost;
        this.member = member;
        this.status = b;
    }

    public void updateStatus(boolean b) {
        this.status = b;
    }
}
