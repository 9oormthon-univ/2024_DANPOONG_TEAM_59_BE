package com.goorm.dapum.domain.chatroom.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.post.entity.Post;
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

    @Enumerated(EnumType.STRING) // Enum 타입 매핑
    @Column(nullable = false)
    private ChatRoomTag chatRoomTag; // 채팅방 태그 ("돌봄" 또는 "나눔")

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id", nullable = true)
    private CarePost carePost;  // 해당 채팅방이 속한 CarePost

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;  // 해당 채팅방이 속한Post

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();  // 해당 채팅방의 메시지들

    @Column(nullable = false)
    private boolean tradeCompleted = false; // 거래 완료 여부

    @Column(nullable = false)
    private boolean reviewCompleted = false; // 후기 작성 여부

    // 생성자
    public ChatRoom(Member member1, Member member2, ChatRoomTag chatRoomTag) {
        this.member1 = member1;
        this.member2 = member2;
        this.chatRoomTag = chatRoomTag;
    }

    public ChatRoom() {

    }

    // CarePost 기반 채팅방 생성
    public static ChatRoom createCareChatRoom(Member member1, Member member2, CarePost carePost) {
        ChatRoom chatRoom = new ChatRoom(member1, member2, ChatRoomTag.CARE);
        chatRoom.setCarePost(carePost);
        return chatRoom;
    }

    // Post 기반 채팅방 생성
    public static ChatRoom createPostChatRoom(Member member1, Member member2, Post post) {
        ChatRoom chatRoom = new ChatRoom(member1, member2, ChatRoomTag.SHARE);
        chatRoom.setPost(post);
        return chatRoom;
    }

    // 거래 완료 설정
    public void completeTrade() {
        this.tradeCompleted = true;
    }

    // 후기 작성 완료 설정
    public void completeReview() {
        this.reviewCompleted = true;
    }
}
