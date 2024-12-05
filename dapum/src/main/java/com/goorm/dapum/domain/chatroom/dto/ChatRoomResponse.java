package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatRoomResponse(
        Long chatRoomId,          // 채팅방 ID
        Long carePostId,          // CarePost ID
        Long postId,              // 일반 게시글 ID (CarePost와 관련 없음)
        String otherUserName,     // 상대방 이름
        String otherProfileImage, // 상대방 프로필 이미지 URL
        String title,             // 게시글의 제목 (CarePost 또는 Post)
        String state,             // 게시글 상태 (CarePost 또는 Post 상태)
        LocalDateTime updatedAt,  // 게시글 마지막 수정 시간
        List<MessageResponse> messages  // 메시지 목록
) {
    public static ChatRoomResponse from(ChatRoom chatRoom, List<MessageResponse> messages, Member currentUser) {
        // 상대방 정보 추출
        String otherUserName = chatRoom.getMember1().equals(currentUser)
                ? chatRoom.getMember2().getNickname()
                : chatRoom.getMember1().getNickname();

        String otherProfileImage = chatRoom.getMember1().equals(currentUser)
                ? chatRoom.getMember2().getProfileImageUrl()
                : chatRoom.getMember1().getProfileImageUrl();

        // CarePost와 Post에 대한 정보 설정
        CarePost carePost = chatRoom.getCarePost();
        Post post = chatRoom.getPost();

        // CarePost와 Post의 ID
        Long carePostId = carePost != null ? carePost.getId() : null;
        Long postId = post != null ? post.getId() : null;

        // CarePost와 Post의 제목 및 상태
        String title = carePost != null ? carePost.getTitle() : (post != null ? post.getTitle() : "제목 없음");
        String state = carePost != null ? carePost.getTag().getDisplayName() : (post != null ? "나눔" : "상태 없음");

        // 마지막 수정 시간 (CarePost 또는 Post의 수정 시간)
        LocalDateTime updatedAt = carePost != null
                ? carePost.getUpdatedAt() // CarePost의 수정 시간
                : (post != null ? post.getUpdatedAt() : LocalDateTime.now()); // Post의 수정 시간 (없으면 현재 시간)

        // ChatRoomResponse 객체 생성
        return new ChatRoomResponse(
                chatRoom.getId(),
                carePostId,
                postId,
                otherUserName,
                otherProfileImage,
                title,
                state,
                updatedAt,
                messages
        );
    }
}
