package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import com.goorm.dapum.domain.memberReview.entity.MemberReview;
import com.goorm.dapum.domain.notification.entity.Notification;
import com.goorm.dapum.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private Neighborhood neighborhood;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Double temperature;
    private int points; // 포인트 필드 추가, 기본값 100

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarePost> carePosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberReview> reviewsGiven = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberReview> reviewsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    public Member() {
    }

    public Member(MemberRequest request) {
        this.kakaoId = request.kakaoId();
        this.kakaoName = request.kakaoName();
        this.nickname = request.nickname();
        this.profileImageUrl = request.profileImageUrl();
        this.email = request.email();
        this.status = Status.ACTIVE;
        this.temperature = 36.5;
        this.points = 100;
    }

    public void updateNickname(Nickname nickname) {
        this.nickname = nickname.nickname();
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void addTemperature(Double temperature) {
        this.temperature += temperature;
    }

    public void deductTemperature(Double temperature) {
        this.temperature -= temperature;
    }

    // 포인트 증가 메서드
    public void addPoints(int points) {
        this.points += points;
    }

    // 포인트 차감 메서드
    public void deductPoints(int points) {
        if (this.points < points) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }
        this.points -= points;
    }
}
