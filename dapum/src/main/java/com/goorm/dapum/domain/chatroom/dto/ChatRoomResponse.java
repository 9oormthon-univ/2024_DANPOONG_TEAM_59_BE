package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatRoomResponse(
        Long chatRoomId,          // 채팅방 ID
        Long carePostId,          // CarePost ID
        String otherUserName,     // 상대방 이름
        String otherProfileImage, // 상대방 프로필 이미지 URL
        String title,             // 게시글의 제목 (CarePost)
        String tag,             // 게시글 상태 (CarePost 상태)
        LocalDateTime updatedAt,  // 게시글 마지막 수정 시간
        boolean tradeCompleted,   // 거래가 완료되었으면 true, 아니면 false
        boolean reviewCompleted,  // 후기가 작성되었으면 true, 아니면 false
        List<MessageResponse> messages  // 메시지 목록
) {
    public static ChatRoomResponse from(
            ChatRoom chatRoom,
            List<MessageResponse> messages,
            Member currentUser,
            boolean tradeCompleted,
            boolean reviewCompleted
    ) {
        // 상대방 정보 추출
        Member otherUser = chatRoom.getMember1().equals(currentUser)
                ? chatRoom.getMember2()
                : chatRoom.getMember1();
        String otherUserName = otherUser.getNickname();
        String otherProfileImage = otherUser.getProfileImageUrl();

        // CarePost 정보 확인
        CarePost carePost = chatRoom.getCarePost();

        // CarePost 관련 값 설정
        Long carePostId = carePost != null ? carePost.getId() : null;
        String title = carePost != null ? carePost.getTitle() : "제목 없음";
        String tag = carePost != null ? carePost.getCarePostTag().getDisplayName() : "상태 없음";
        LocalDateTime updatedAt = carePost != null ? carePost.getUpdatedAt() : LocalDateTime.now();

        // ChatRoomResponse 객체 생성
        return new ChatRoomResponse(
                chatRoom.getId(),
                carePostId,
                otherUserName,
                otherProfileImage,
                title,
                tag,
                updatedAt,
                tradeCompleted,  // 거래 완료 여부
                reviewCompleted, // 후기 작성 완료 여부
                messages
        );
    }
}
