package com.goorm.dapum.domain.carePost.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.careComment.entity.CareComment;
import com.goorm.dapum.domain.carePostLike.entity.CarePostLike;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import jakarta.persistence.*;
import com.goorm.dapum.domain.carePost.dto.CarePostRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_post_id")
    private Long id;  // 게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 작성자 (Member 엔티티와의 관계)

    @Column(nullable = false)
    private String title;  // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 내용 (긴 텍스트 지원)

    @OneToMany(mappedBy = "carePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "carePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarePostLike> likes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "care_post_image_urls", joinColumns = @JoinColumn(name = "care_post_id"))
    @Column(name = "image_url", length = 2083)
    private List<String> imageUrls = new ArrayList<>();  // 게시글 이미지 URL 목록

    @Column(nullable = false)
    private Tag tag;  // 게시글 키워드 목록

    private boolean isEmergency; // 긴급 돌봄 유무

    @Column(nullable = false)
    private LocalDate careDate;  // 돌봄을 원하는 날짜

    @Column(nullable = false)
    private LocalTime startTime;  // 돌봄 시작 시간

    @Column(nullable = false)
    private LocalTime endTime;  // 돌봄 끝나는 시간

    // 게시글과 관련된 채팅방들
    @OneToMany(mappedBy = "carePost")
    private List<ChatRoom> chatRooms = new ArrayList<>(); // 여러 채팅방이 연결될 수 있음

    // 생성자: DTO를 사용하여 엔티티 생성
    public CarePost(Member member, CarePostRequest request) {
        this.member = member;
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tag = request.tag(); // 추가된 필드
        this.isEmergency = request.isEmergency(); // 추가된 필드
        this.careDate = request.careDate();
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }

    // 업데이트 메서드: DTO를 사용하여 엔티티 업데이트
    public void update(CarePostRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tag = request.tag(); // 추가된 필드
        this.isEmergency = request.isEmergency(); // 추가된 필드
        this.careDate = request.careDate();
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }
}
