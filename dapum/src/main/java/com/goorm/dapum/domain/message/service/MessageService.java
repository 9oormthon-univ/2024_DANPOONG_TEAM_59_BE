package com.goorm.dapum.domain.message.service;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.dto.SendRequest;
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
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // 메시지 전송
    public boolean sendMessage(Long chatRoomId, SendRequest request) {
        // 현재 로그인된 사용자(발신자) 찾기
        Member sender = memberService.findMember();

        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        // 수신자 설정
        Member receiver = chatRoom.getMember1().equals(sender) ? chatRoom.getMember2() : chatRoom.getMember1();

        // 메시지 생성
        Message message = new Message(sender, receiver, request.content(), false);
        message.setChatRoom(chatRoom);

        // 이미지 URL 추가 (null 체크 및 초기화)
        if (request.imageUrls() != null && !request.imageUrls().isEmpty()) {
            message.getImageUrls().addAll(request.imageUrls());
        }

        // 메시지 저장
        messageRepository.save(message);
        return true;
    }


    public void markMessagesAsRead(Long chatRoomId, Member receiver) {
        List<Message> unreadMessages = messageRepository.findByChatRoomIdAndReceiverIdAndIsReadFalse(chatRoomId, receiver.getId());
        for (Message message : unreadMessages) {
            message.setRead(true);
        }
        messageRepository.saveAll(unreadMessages);
    }
}
