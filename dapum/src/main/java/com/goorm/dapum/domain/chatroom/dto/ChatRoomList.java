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
        String state // 게시글들의 상태 돌봄 완료, 예약중
) {
    public static ChatRoomList from(ChatRoom chatRoom, Member currentUser) {
        // 상대방 정보 추출
        Member otherUser = chatRoom.getMember1().equals(currentUser) ? chatRoom.getMember2() : chatRoom.getMember1();
        String otherUserName = otherUser.getKakaoName();
        String otherUserNeighborhood = otherUser.getNeighborhood().getDistrict();
        String otherProfileImage = otherUser.getProfileImageUrl();

        // 마지막 메시지 및 읽지 않은 메시지 개수 계산
        String lastMessage = chatRoom.getMessages().isEmpty()
                ? ""
                : chatRoom.getMessages().getLast().getContent();
        int unreadMessageCount = (int) chatRoom.getMessages().stream()
                .filter(message -> !message.isRead() && message.getReceiver().equals(currentUser))
                .count();

        // CarePost 정보 기반 태그 및 상태 결정
        CarePost carePost = chatRoom.getCarePost();
        String tag = carePost != null && carePost.getMember().equals(currentUser) ? "돌봄 제공" : "돌봄받기";
        String state = carePost != null ? carePost.getCarePostTag().getDisplayName() : "상태 없음";

        return new ChatRoomList(
                chatRoom.getId(),
                otherUserName,
                otherUserNeighborhood,
                otherProfileImage,
                lastMessage,
                unreadMessageCount,
                tag,
                state
        );
    }
}
