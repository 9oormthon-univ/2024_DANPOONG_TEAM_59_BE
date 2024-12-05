package com.goorm.dapum.domain.notification.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.notification.dto.NotificationRequest;
import com.goorm.dapum.domain.notification.dto.NotificationSetting;
import com.goorm.dapum.domain.notification.dto.NotificationToken;
import com.goorm.dapum.domain.notification.entity.Notification;
import com.goorm.dapum.domain.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final MemberService memberService;

    public void saveToken(NotificationRequest request) {
        Member member = memberService.findMember();
        Notification notification = new Notification(member, request);
        notificationRepository.save(notification);
    }

    public void updateNotificationSettings(NotificationSetting setting) {
        Member member = memberService.findMember();
        Notification notification = (Notification) notificationRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자의 notification 토큰 설정이 되지 않았습니다. "));
        notification.update(setting);
        notificationRepository.save(notification);
    }

    public List<NotificationToken> getInAppNotificationTokens() {
        return notificationRepository.findByNotificationEnabledTrue()
                .stream()
                .map(notification -> new NotificationToken(notification.getNotificationToken()))
                .collect(Collectors.toList());
    }

}
