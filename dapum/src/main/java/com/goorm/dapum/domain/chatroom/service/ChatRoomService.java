package com.goorm.dapum.domain.chatroom.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomList;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomRequest;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomResponse;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.entity.ChatRoomTag;
import com.goorm.dapum.domain.chatroom.entity.TradeState;
import com.goorm.dapum.domain.chatroom.dto.TradeStateRequest;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.message.repository.MessageRepository;
import com.goorm.dapum.domain.message.service.MessageService;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final CarePostRepository carePostRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final MessageService messageService;

    // CarePost 또는 Post 기반 채팅방 생성 또는 가져오기
    public ChatRoomResponse findOrCreateChatRoom(ChatRoomRequest request) {
        Member currentUser = memberService.findMember();
        ChatRoom chatRoom;

        // `tag` 값에 따라 CarePost 또는 Post로 처리
        if ("돌봄".equals(request.tag())) {
            CarePost carePost = carePostRepository.findById(request.id())
                    .orElseThrow(() -> new IllegalArgumentException("CarePost를 찾을 수 없습니다."));

            Member otherMember = carePost.getMember();

            // CarePost 기반 채팅방 생성 또는 가져오기
            chatRoom = chatRoomRepository.findByCarePostId(request.id())
                    .orElseGet(() -> ChatRoom.createCareChatRoom(currentUser, otherMember, carePost));
        } else if ("나눔".equals(request.tag())) {
            Post post = postRepository.findById(request.id())
                    .orElseThrow(() -> new IllegalArgumentException("Post를 찾을 수 없습니다."));

            Member otherMember = post.getMember();

            // Post 기반 채팅방 생성 또는 가져오기
            chatRoom = chatRoomRepository.findByPostId(request.id())
                    .orElseGet(() -> ChatRoom.createPostChatRoom(currentUser, otherMember, post));
        } else {
            throw new IllegalArgumentException("유효하지 않은 tag 값입니다. '돌봄' 또는 '나눔'을 사용하세요.");
        }

        // 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        // 메시지 가져오기
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoom.getId());
        List<MessageResponse> messageResponses = messages.stream()
                .map(MessageResponse::new)
                .toList();

        // 안읽은 메시지 읽음 처리
        messageService.markMessagesAsRead(chatRoom.getId(), currentUser);

        // 거래 완료 상태
        String tradeState = chatRoom.getTradeState().getDisplayName();

        // 현재 사용자의 리뷰 작성 상태 확인
        boolean reviewCompleted;
        if (chatRoom.getMember1().equals(currentUser)) {
            reviewCompleted = chatRoom.isMember1ReviewCompleted();
        } else if (chatRoom.getMember2().equals(currentUser)) {
            reviewCompleted = chatRoom.isMember2ReviewCompleted();
        } else {
            throw new IllegalArgumentException("현재 사용자는 이 채팅방의 참여자가 아닙니다.");
        }

        // ChatRoomResponse 생성
        return ChatRoomResponse.from(chatRoom, messageResponses, currentUser, tradeState, reviewCompleted);
    }

    // 현재 사용자의 채팅 목록 가져오기
    public List<ChatRoomList> getChatRooms() {
        Member currentUser = memberService.findMember();

        // 현재 사용자가 포함된 모든 채팅방 조회
        List<ChatRoom> chatRooms = chatRoomRepository.findByMember(currentUser);

        // 채팅방 목록 변환
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomList.from(chatRoom, currentUser))
                .toList();
    }

    // 거래 상태 바꿈
    public void changeTradeState(TradeStateRequest request) throws IllegalAccessException {
        Member currentUser = memberService.findMember();
        ChatRoom chatRoom = chatRoomRepository.findById(request.chatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        TradeState newTradeState = TradeState.fromDisplayName(request.tradeState());

        // 거래 상태 변경 권한 확인 및 변경
        if (chatRoom.getChatRoomTag() == ChatRoomTag.SHARE) { // 나눔
            Post post = chatRoom.getPost();
            if (post.getMember().equals(currentUser)) {
                chatRoom.changeTradeState(newTradeState);
            } else {
                throw new IllegalAccessException("게시글 작성자만 거래 상태를 변경할 수 있습니다.");
            }
        } else if (chatRoom.getChatRoomTag() == ChatRoomTag.CARE) { // 돌봄
            CarePost carePost = chatRoom.getCarePost();
            if (carePost.getMember().equals(currentUser)) {
                chatRoom.changeTradeState(newTradeState);
            } else {
                throw new IllegalAccessException("게시글 작성자만 거래 상태를 변경할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 ChatRoomTag입니다.");
        }
    }
}
