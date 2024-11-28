package com.goorm.dapum.domain.post.entity;

import com.goorm.dapum.core.base.BaseEntity;
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
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();  // 게시글 이미지 URL 목록

    @ElementCollection // 값 타입 컬렉션 매핑
    @CollectionTable(name = "post_keywords", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();  // 게시글 키워드 목록

    public Post(Member member, PostRequest request) {
        this.member = member;
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tags = request.tags();
    }

    public void update(PostRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tags = request.tags();
    }
}
