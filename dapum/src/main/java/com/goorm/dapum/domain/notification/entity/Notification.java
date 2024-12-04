package com.goorm.dapum.domain.notification.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.notification.dto.NotificationRequest;
import com.goorm.dapum.domain.notification.dto.NotificationSetting;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "notification_token", nullable = false)
    private String notificationToken;

    @Column(name = "is_notification_enabled", nullable = false)
    private boolean notificationEnabled;

    public Notification(Member member, NotificationRequest request) {
        this.member = member;
        this.notificationToken = request.token();
        this.notificationEnabled = request.notificationEnabled();
    }

    public void update(NotificationSetting setting) {
        this.notificationEnabled = setting.notificationEnabled();
    }
}
