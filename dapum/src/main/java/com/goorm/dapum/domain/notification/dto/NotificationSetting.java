package com.goorm.dapum.domain.notification.dto;

public record NotificationSetting(
        boolean notificationEnabled,
        boolean pushEnabled
) {
}
