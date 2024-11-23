package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.application.dto.member.Neighborhood;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
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
    private String neighborhood;

    public Member() {

    }

    public Member(MemberRequest request) {
        this.kakaoId = request.kakaoId();
        this.kakaoName = request.kakaoName();
        this.nickname = request.nickname();
        this.profileImageUrl = request.profileImageUrl();
        this.email = request.email();
    }

    public void updateNickname(Nickname nickname) {
        this.nickname = nickname.nickname();
    }

    public void updateNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood.neighborhood();
    }
}
