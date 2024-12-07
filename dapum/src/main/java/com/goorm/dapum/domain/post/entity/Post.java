package com.goorm.dapum.domain.post.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.PostReport.entity.PostReport;
import com.goorm.dapum.domain.carePostReport.entity.CareReport;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.comment.entity.Comment;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.dto.PostRequest;
import com.goorm.dapum.domain.postLike.entity.PostLike;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;  // 게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY) // Lazy 로딩으로 성능 최적화
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 작성자 (Member 엔티티와의 관계)

    @Column(nullable = false)
    private String title;  // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 내용 (긴 텍스트 지원)

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    @ElementCollection // 값 타입 컬렉션 매핑
    @CollectionTable(name = "post_image_urls", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url", length = 2083)
    private List<String> imageUrls = new ArrayList<>();  // 게시글 이미지 URL 목록

    @ElementCollection(fetch = FetchType.LAZY) // 값 타입 컬렉션 매핑
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Enumerated(EnumType.STRING) // Enum을 String 형태로 저장
    @Column(name = "tag")
    private List<PostTag> postTags = new ArrayList<>();  // 게시글 태그 목록 (Enum)

    // 게시글과 관련된 채팅방들
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>(); // 여러 채팅방이 연결될 수 있음

    // 신고된 게시글들을 삭제할 수 있도록 CareReport와 관계 추가
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReport> postReports = new ArrayList<>();  // 관련된 신고들


    public Post(Member member, PostRequest request) {
        this.member = member;
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.postTags = convertStringsToTags(request.postTags()); // 요청에서 받아온 태그 설정
    }

    public void update(PostRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.postTags = convertStringsToTags(request.postTags());  // 태그 업데이트
    }

    private List<PostTag> convertStringsToTags(List<String> tagStrings) {
        if (tagStrings == null || tagStrings.isEmpty()) {
            return new ArrayList<>();
        }
        List<PostTag> postTags = new ArrayList<>();
        for (String tagString : tagStrings) {
            try {
                postTags.add(PostTag.fromDisplayName(tagString));
            } catch (IllegalArgumentException e) {
                // 처리하지 못하는 태그는 로그를 남기거나 무시
            }
        }
        return postTags;
    }

    public void addPostTags(String tag){
        this.postTags.add(PostTag.fromDisplayName(tag));
    }
}
