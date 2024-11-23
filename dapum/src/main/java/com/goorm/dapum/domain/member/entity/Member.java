package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private Long kakaoId;
    private String kakaoName;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public Member() {

    }

    public Member(MemberRequest request) {
        this.kakaoId = request.kakaoId();
        this.kakaoName = request.kakaoName();
        this.nickname = request.nickname();
        this.profileImageUrl = request.profileImageUrl();
        this.email = request.email();
    }

}
