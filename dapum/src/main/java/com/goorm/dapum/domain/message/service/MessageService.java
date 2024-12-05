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
        Member sender = memberService.findMember();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        Message message = new Message(sender, chatRoom.getMember1().equals(sender) ? chatRoom.getMember2() : chatRoom.getMember1(), request.content(), false);
        message.setChatRoom(chatRoom);
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
