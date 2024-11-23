package com.goorm.dapum.domain.supportInfo.entity;

import com.goorm.dapum.core.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class SupportInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_info_id")
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String link;  // 필수는 아니므로 nullable 허용
}

