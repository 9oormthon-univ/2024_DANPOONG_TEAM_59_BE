package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.application.dto.member.Neighborhood;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import com.goorm.dapum.domain.memberReview.entity.MemberReview;
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
    private String neighborhood;
    private Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarePost> carePosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberReview> reviewsGiven = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberReview> reviewsReceived = new ArrayList<>();

    public Member() {

    }

    public Member(MemberRequest request) {
        this.kakaoId = request.kakaoId();
        this.kakaoName = request.kakaoName();
        this.nickname = request.nickname();
        this.profileImageUrl = request.profileImageUrl();
        this.email = request.email();
        this.status = Status.ACTIVE;
    }

    public void updateNickname(Nickname nickname) {
        this.nickname = nickname.nickname();
    }

    public void updateNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood.neighborhood();
    }
}
