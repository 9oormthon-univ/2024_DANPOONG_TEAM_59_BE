package com.goorm.dapum.domain.carePost.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import com.goorm.dapum.domain.carePost.dto.CarePostRequest;
import lombok.*;
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
    @Column(name = "care_post_id")  // 컬럼 이름을 명확히 수정
    private Long id;  // 게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)  // Lazy 로딩으로 성능 최적화
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 작성자 (Member 엔티티와의 관계)

    @Column(nullable = false)
    private String title;  // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 내용 (긴 텍스트 지원)

    @ElementCollection // 값 타입 컬렉션 매핑
    @CollectionTable(name = "care_post_image_urls", joinColumns = @JoinColumn(name = "care_post_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();  // 게시글 이미지 URL 목록

    @ElementCollection // 값 타입 컬렉션 매핑
    @CollectionTable(name = "care_post_keywords", joinColumns = @JoinColumn(name = "care_post_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();  // 게시글 키워드 목록

    // 생성자: DTO를 사용하여 엔티티 생성
    public CarePost(Member member, CarePostRequest request) {
        this.member = member;
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tags = request.tags();
    }

    // 업데이트 메서드: DTO를 사용하여 엔티티 업데이트
    public void update(CarePostRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.imageUrls = request.imageUrls();
        this.tags = request.tags();
    }
}