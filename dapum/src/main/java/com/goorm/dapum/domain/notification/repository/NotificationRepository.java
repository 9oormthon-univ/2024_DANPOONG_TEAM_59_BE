package com.goorm.dapum.domain.notification.repository;

import com.goorm.dapum.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByMemberId(Long id);

    Collection<Notification> findByNotificationEnabledTrue();

}

