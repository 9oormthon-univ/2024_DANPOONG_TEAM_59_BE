package com.goorm.dapum.domain.message.entity;


import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;  // 메시지가 속한 채팅방

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;  // 발신자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;  // 수신자

    @Column(nullable = false)
    private String content;  // 메시지 내용

    @ElementCollection
    @CollectionTable(name = "message_image_urls", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "image_url", length = 2083)
    private List<String> imageUrls = new ArrayList<>();  // 게시글 이미지 URL 목록

    @Column(nullable = false)
    private boolean isRead = false;  // 읽음 여부 (기본값 false)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 전송 시간

    public Message(Member sender, Member receiver, String content, boolean b) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isRead = b;
    }

    public Message() {

    }

    // @PrePersist 메서드로 생성 시간 자동 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

