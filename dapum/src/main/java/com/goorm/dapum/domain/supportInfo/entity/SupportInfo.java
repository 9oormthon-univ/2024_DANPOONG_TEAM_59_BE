package com.goorm.dapum.domain.supportInfo.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.supportInfo.dto.SupportDetail;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class SupportInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_info_id")
    private Long id;

    @ElementCollection // List<String>을 JPA에서 매핑하기 위해 사용
    @CollectionTable(name = "support_info_category", joinColumns = @JoinColumn(name = "support_info_id"))
    @Column(name = "category", nullable = false)
    private List<String> category = new ArrayList<>();

    @Column(nullable = false)
    private String department; // 지원하는 부서

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String url;  // 필수는 아니므로 nullable 허용 // 필수는 아니므로 nullable 허용

    public SupportDetail detail() {
        return new SupportDetail(this.id, this.category, this.department, this.title, this.content, this.url, this.getUpdatedAt());
    }
}

