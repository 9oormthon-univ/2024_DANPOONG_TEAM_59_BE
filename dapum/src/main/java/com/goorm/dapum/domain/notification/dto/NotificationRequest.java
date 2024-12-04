package com.goorm.dapum.domain.notification.dto;

public record NotificationRequest(
        String token,
        boolean notificationEnabled
) {
}
