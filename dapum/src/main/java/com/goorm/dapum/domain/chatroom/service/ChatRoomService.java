package com.goorm.dapum.domain.chatroom.service;

import com.goorm.dapum.domain.chatroom.dto.ChatRoomList;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomResponse;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.message.repository.MessageRepository;
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

    // 채팅방 생성 기존에 있으면 가져오기
    public ChatRoomResponse findOrCreateChatRoom(Long meber2Id) {
        Member member1 = memberService.findMember();
        Member member2 = memberService.findById(meber2Id);
        // 채팅방 찾기
        ChatRoom chatRoom = chatRoomRepository.findByMembers(member1, member2)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setMember1(member1);
                    newChatRoom.setMember2(member2);
                    return chatRoomRepository.save(newChatRoom);
                });

        // 메시지 내용 가져오기
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoom.getId());

        // ChatRoomResponse에 채팅방 정보와 메시지 내용 포함
        return new ChatRoomResponse(chatRoom, messages.stream()
                .map(MessageResponse::new)
                .toList());
    }

    // 채팅 목록 가져오기
    public List<ChatRoomList> getChatRooms() {
        Member member = memberService.findMember();
        List<ChatRoom> chatRooms = chatRoomRepository.findByMember(member);

        return chatRooms.stream().map(chatRoom -> {
            Member otherMember = chatRoom.getMember1().equals(member) ? chatRoom.getMember2() : chatRoom.getMember1();

            // 채팅방의 마지막 메시지
            Message lastMessage = messageRepository.findTopByChatRoomOrderByCreatedAtDesc(chatRoom)
                    .orElse(null);

            // 읽지 않은 메시지 수 계산
            int unreadMessageCount = messageRepository.countByChatRoomAndReceiverAndIsReadFalse(chatRoom, member);

            return ChatRoomList.builder()
                    .chatRoomId(chatRoom.getId())
                    .otherUserName(otherMember.getKakaoName())
                    .otherUserNeighborhood(otherMember.getNeighborhood() != null ? otherMember.getNeighborhood().toString() : "미설정")
                    .lastMessage(lastMessage != null ? lastMessage.getContent() : "대화 없음")
                    .unreadMessageCount(unreadMessageCount)
                    .build();
        }).toList();
    }
}
