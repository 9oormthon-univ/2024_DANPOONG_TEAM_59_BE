package com.goorm.dapum.domain.chatroom.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id", nullable = false)
    private Member member1;  // 참여자 1

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id", nullable = false)
    private Member member2;  // 참여자 2

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id", nullable = true)
    private CarePost carePost;  // 해당 채팅방이 속한 CarePost

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();  // 해당 채팅방의 메시지들

}
