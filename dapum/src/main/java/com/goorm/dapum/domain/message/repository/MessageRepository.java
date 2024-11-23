package com.goorm.dapum.domain.message.repository;

import com.goorm.dapum.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // 발신자 또는 수신자 기준으로 쪽지 조회
    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAt(Long senderId, Long receiverId);

    // 특정 발신자와 수신자 간 메시지 조회
    List<Message> findBySenderIdAndReceiverIdOrderByCreatedAt(Long senderId, Long receiverId);

    // 읽지 않은 메시지 개수
    long countByReceiverIdAndIsReadFalse(Long receiverId);
}

