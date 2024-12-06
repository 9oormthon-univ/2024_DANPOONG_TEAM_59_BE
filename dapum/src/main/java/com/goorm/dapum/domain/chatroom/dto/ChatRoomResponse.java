package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatRoomResponse(
        Long chatRoomId,          // 채팅방 ID
        Long id,                  // CarePost ID 또는 Post ID
        String otherUserName,     // 상대방 이름
        String otherProfileImage, // 상대방 프로필 이미지 URL
        String title,             // 게시글 제목
        String tag,               // ChatRoom 태그 (돌봄, 나눔)
        LocalDateTime updatedAt,  // 게시글 마지막 수정 시간
        boolean tradeCompleted,   // 거래 완료 여부
        boolean reviewCompleted,  // 후기 작성 완료 여부
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

        // CarePost와 Post 정보 확인
        CarePost carePost = chatRoom.getCarePost();
        Post post = chatRoom.getPost();

        // 게시글 관련 값 설정
        Long id = carePost != null ? carePost.getId() : (post != null ? post.getId() : null);
        String title = carePost != null ? carePost.getTitle() : (post != null ? post.getTitle() : "제목 없음");
        String tag = chatRoom.getChatRoomTag().getDisplayName(); // ChatRoom의 태그 사용
        LocalDateTime updatedAt = carePost != null
                ? carePost.getUpdatedAt()
                : (post != null ? post.getUpdatedAt() : LocalDateTime.now());

        // ChatRoomResponse 객체 생성
        return new ChatRoomResponse(
                chatRoom.getId(),
                id,
                otherUserName,
                otherProfileImage,
                title,
                tag,
                updatedAt,
                tradeCompleted,
                reviewCompleted,
                messages
        );
    }
}
