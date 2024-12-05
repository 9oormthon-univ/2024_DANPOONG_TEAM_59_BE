package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import lombok.Builder;

@Builder
public record ChatRoomList(
        Long chatRoomId, // 채팅방 ID
        String otherUserName, // 상대방 이름
        String otherUserNeighborhood, // 상대방 동네
        String otherProfileImage, // 상대방 이미지
        String lastMessage, // 마지막 메시지
        int unreadMessageCount, // 안 읽은 메시지 개수
        String tag, // 돌봄지원, 돌봄받기, 나눔받기
        String state // 게시글들의 상태
) {
    public static ChatRoomList from(ChatRoom chatRoom, Member currentUser) {
        // 상대방 정보 추출
        Member otherUser = chatRoom.getMember1().equals(currentUser) ? chatRoom.getMember2() : chatRoom.getMember1();
        String otherUserName = otherUser.getKakaoName();
        String otherUserNeighborhood = otherUser.getNeighborhood().getDistrict();
        String profileImage = otherUser.getProfileImageUrl();

        // 마지막 메시지 및 읽지 않은 메시지 개수 계산
        String lastMessage = chatRoom.getMessages().isEmpty()
                ? ""
                : chatRoom.getMessages().get(chatRoom.getMessages().size() - 1).getContent();
        int unreadMessageCount = (int) chatRoom.getMessages().stream()
                .filter(message -> !message.isRead() && message.getReceiver().equals(currentUser))
                .count();

        // tag 및 state 결정
        String tag;
        String state = "";

        CarePost carePost = chatRoom.getCarePost();

        if (carePost != null) {
            tag = carePost.getMember().equals(currentUser) ? "돌봄 제공" : "돌봄받기";
            state = carePost.getTag().getDisplayName(); // CarePost의 상태 사용
        } else {
            tag = "나눔받기";
        }

        return new ChatRoomList(
                chatRoom.getId(),
                otherUserName,
                otherUserNeighborhood,
                profileImage,
                lastMessage,
                unreadMessageCount,
                tag,
                state
        );
    }
}
