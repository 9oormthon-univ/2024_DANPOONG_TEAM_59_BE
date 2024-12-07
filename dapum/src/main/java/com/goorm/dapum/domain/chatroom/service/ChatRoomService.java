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
import java.util.Optional;

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
        ChatRoom chatRoom = findOrCreateChatRoomByTag(request, currentUser);

        chatRoom = saveChatRoom(chatRoom);

        List<MessageResponse> messageResponses = getMessageResponses(chatRoom);

        messageService.markMessagesAsRead(chatRoom.getId(), currentUser);

        String tradeState = getTradeState(chatRoom);

        boolean reviewCompleted = checkReviewCompletion(chatRoom, currentUser);

        return ChatRoomResponse.from(chatRoom, messageResponses, currentUser, tradeState, reviewCompleted);
    }

    // ChatRoom 생성 또는 찾기
    private ChatRoom findOrCreateChatRoomByTag(ChatRoomRequest request, Member currentUser) {
        if ("돌봄".equals(request.tag())) {
            return findOrCreateCareChatRoom(request, currentUser);
        } else if ("나눔".equals(request.tag())) {
            return findOrCreateShareChatRoom(request, currentUser);
        } else {
            throw new IllegalArgumentException("유효하지 않은 tag 값입니다. '돌봄' 또는 '나눔'을 사용하세요.");
        }
    }

    // CarePost 기반 ChatRoom 생성 또는 찾기
    private ChatRoom findOrCreateCareChatRoom(ChatRoomRequest request, Member currentUser) {
        CarePost carePost = carePostRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("CarePost를 찾을 수 없습니다."));

        // 게시글 작성자와 현재 사용자 비교
        if (currentUser.getId().equals(carePost.getMember().getId())) {
            throw new IllegalArgumentException("자신의 CarePost로는 채팅방을 생성할 수 없습니다.");
        }

        Member otherMember = carePost.getMember();

        // 현재 사용자와 게시글 작성자로 구성된 기존 채팅방 검색
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByCarePostAndMembers(carePost, currentUser, otherMember);

        // 기존 채팅방이 있으면 반환, 없으면 새로 생성
        return existingChatRoom.orElseGet(() -> ChatRoom.createCareChatRoom(currentUser, otherMember, carePost));
    }

    // Post 기반 ChatRoom 생성 또는 찾기
    private ChatRoom findOrCreateShareChatRoom(ChatRoomRequest request, Member currentUser) {
        Post post = postRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Post를 찾을 수 없습니다."));

        // 게시글 작성자와 현재 사용자 비교
        if (currentUser.getId().equals(post.getMember().getId())) {
            throw new IllegalArgumentException("자신의 Post로는 채팅방을 생성할 수 없습니다.");
        }

        Member otherMember = post.getMember();

        // 현재 사용자와 게시글 작성자로 구성된 기존 채팅방 검색
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByPostAndMembers(post, currentUser, otherMember);

        // 기존 채팅방이 있으면 반환, 없으면 새로 생성
        return existingChatRoom.orElseGet(() -> ChatRoom.createPostChatRoom(currentUser, otherMember, post));
    }

    // ChatRoom 저장
    private ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    // 메시지 목록 가져오기 및 변환
    private List<MessageResponse> getMessageResponses(ChatRoom chatRoom) {
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoom.getId());
        return messages.stream()
                .map(MessageResponse::new)
                .toList();
    }

    // 거래 상태 가져오기
    private String getTradeState(ChatRoom chatRoom) {
        return chatRoom.getTradeState().getDisplayName();
    }

    // 리뷰 완료 여부 확인
    private boolean checkReviewCompletion(ChatRoom chatRoom, Member currentUser) {
        if (chatRoom.getMember1().equals(currentUser)) {
            return chatRoom.isMember1ReviewCompleted();
        } else if (chatRoom.getMember2().equals(currentUser)) {
            return chatRoom.isMember2ReviewCompleted();
        } else {
            throw new IllegalArgumentException("현재 사용자는 이 채팅방의 참여자가 아닙니다.");
        }
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

    public ChatRoomResponse getChatRoom(Long chatRoomId) {
        Member currentUser = memberService.findMember();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        List<MessageResponse> messageResponses = getMessageResponses(chatRoom);

        // 안읽은 메시지 읽음 처리
        messageService.markMessagesAsRead(chatRoom.getId(), currentUser);

        String tradeState = getTradeState(chatRoom);

        boolean reviewCompleted = checkReviewCompletion(chatRoom, currentUser);

        return ChatRoomResponse.from(chatRoom, messageResponses, currentUser, tradeState, reviewCompleted);
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
                post.addPostTags(request.tradeState());
            } else {
                throw new IllegalAccessException("게시글 작성자만 거래 상태를 변경할 수 있습니다.");
            }
        } else if (chatRoom.getChatRoomTag() == ChatRoomTag.CARE) { // 돌봄
            CarePost carePost = chatRoom.getCarePost();
            if (carePost.getMember().equals(currentUser)) {
                chatRoom.changeTradeState(newTradeState);
                carePost.updateTag(request.tradeState());
                if (newTradeState.equals(TradeState.TRADE_COMPLETED)) {
                    // 돌봄 시간 계산
                    int timeInMinutes = (carePost.getEndTime().getHour() * 60 + carePost.getEndTime().getMinute())
                            - (carePost.getStartTime().getHour() * 60 + carePost.getStartTime().getMinute());

                    // 1시간 당 5포인트
                    int points = (timeInMinutes / 60) * 5;

                    // 포인트 차감 및 증가
                    currentUser.deductPoints(points);
                    Member otherUser = (carePost.getMember().equals(currentUser)) ? chatRoom.getMember1() : chatRoom.getMember2();
                    otherUser.addPoints(points);
                }
            } else {
                throw new IllegalAccessException("게시글 작성자만 거래 상태를 변경할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 ChatRoomTag입니다.");
        }
    }
}
