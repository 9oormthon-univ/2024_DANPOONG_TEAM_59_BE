package com.goorm.dapum.application.controller.notification;

import com.goorm.dapum.domain.notification.dto.NotificationRequest;
import com.goorm.dapum.domain.notification.dto.NotificationSetting;
import com.goorm.dapum.domain.notification.dto.NotificationToken;
import com.goorm.dapum.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    @PostMapping("/token")
    @Operation(summary = "사용자의 토큰 저장")
    public ResponseEntity<Void> saveToken(@RequestParam NotificationRequest request) {
        notificationService.saveToken(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/settings")
    @Operation(summary = "사용자 알람 설정")
    public ResponseEntity<Void> updateNotificationSettings(@RequestBody NotificationSetting setting){
        notificationService.updateNotificationSettings(setting);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tokens/in-app")
    @Operation(summary = "인앱 알람 토큰 전달")
    public ResponseEntity<List<NotificationToken>> getInAppNotificationTokens() {
        List<NotificationToken> tokens = notificationService.getInAppNotificationTokens();
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/tokens/push")
    @Operation(summary = "푸시 알람 토큰 전달")
    public ResponseEntity<List<NotificationToken>> getPushNotificationTokens() {
        List<NotificationToken> tokens = notificationService.getPushNotificationTokens();
        return ResponseEntity.ok(tokens);
    }


}
