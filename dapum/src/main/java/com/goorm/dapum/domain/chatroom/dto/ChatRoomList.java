package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatRoomList(
        Long chatRoomId,           // 채팅방 ID
        String otherUserName,      // 상대방 이름
        String otherUserNeighborhood, // 상대방 동네
        String otherProfileImage,  // 상대방 프로필 이미지
        String lastMessage,        // 마지막 메시지
        int unreadMessageCount,    // 안 읽은 메시지 개수
        String tag,                // ChatRoom의 돌봄, 나눔
        LocalDateTime updatedAt    // 마지막 메시지 또는 게시글 수정 시간
) {
    public static ChatRoomList from(ChatRoom chatRoom, Member currentUser) {
        // 상대방 정보 추출
        Member otherUser = chatRoom.getMember1().equals(currentUser) ? chatRoom.getMember2() : chatRoom.getMember1();
        String otherUserName = otherUser.getKakaoName();
        String otherUserNeighborhood = otherUser.getNeighborhood() != null
                ? otherUser.getNeighborhood().getDistrict()
                : "미설정";
        String otherProfileImage = otherUser.getProfileImageUrl();

        // 마지막 메시지 및 읽지 않은 메시지 개수 계산
        String lastMessage = chatRoom.getMessages().isEmpty()
                ? "대화 없음"
                : chatRoom.getMessages().get(chatRoom.getMessages().size() - 1).getContent();
        int unreadMessageCount = (int) chatRoom.getMessages().stream()
                .filter(message -> !message.isRead() && message.getReceiver().equals(currentUser))
                .count();

        // ChatRoomTag 및 게시글 상태 결정
        CarePost carePost = chatRoom.getCarePost();
        Post post = chatRoom.getPost();
        String tag = chatRoom.getChatRoomTag().getDisplayName();
        LocalDateTime updatedAt = carePost != null
                ? carePost.getUpdatedAt()
                : (post != null ? post.getUpdatedAt() : LocalDateTime.now());

        return new ChatRoomList(
                chatRoom.getId(),
                otherUserName,
                otherUserNeighborhood,
                otherProfileImage,
                lastMessage,
                unreadMessageCount,
                tag,
                updatedAt
        );
    }
}
