package com.goorm.dapum.domain.message.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberService memberService;

    // 메시지 전송
    public void sendMessage(Long receiverId, String content) {
        Member sender = memberService.findMember();
        Member receiver = memberService.findById(receiverId);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setRead(false);

        messageRepository.save(message);
    }

    // 대화 내역 조회
    public List<Message> getConversation(Long memberId) {
        Member loggedInMember = memberService.findMember();
        return messageRepository.findBySenderIdOrReceiverIdOrderByCreatedAt(loggedInMember.getId(), memberId);
    }

    // 읽지 않은 메시지 수
    public long getUnreadMessageCount() {
        Long receiverId = memberService.findMember().getId();
        return messageRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }

    // 메시지 읽음 처리
    public void markMessagesAsRead(Long senderId) {
        Member receiver = memberService.findMember();
        List<Message> unreadMessages = messageRepository.findBySenderIdAndReceiverIdOrderByCreatedAt(senderId, receiver.getId());
        unreadMessages.forEach(message -> message.setRead(true));
        messageRepository.saveAll(unreadMessages);
    }
}
