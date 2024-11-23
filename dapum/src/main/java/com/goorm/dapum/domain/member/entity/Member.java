package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.core.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String kakaoId;
    private String kakaoName;
    private String nickname;
    private String profileImageUrl;
}
