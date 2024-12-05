package com.goorm.dapum.domain.message.repository;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // 읽지 않은 메시지 개수
    long countByReceiverIdAndIsReadFalse(Long receiverId);

    List<Message> findByChatRoomIdOrderByCreatedAtAsc(Long id);

    List<Message> findByChatRoomIdAndReceiverIdAndIsReadFalse(Long chatRoomId, Long id);

    Optional<Message> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    int countByChatRoomAndReceiverAndIsReadFalse(ChatRoom chatRoom, Member receiver);

}

