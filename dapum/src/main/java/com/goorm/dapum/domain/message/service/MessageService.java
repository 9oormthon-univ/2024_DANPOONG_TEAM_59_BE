package com.goorm.dapum.domain.message.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.dto.SendRequest;
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
    public void sendMessage(SendRequest sendRequest) {
        Member sender = memberService.findMember();
        Member receiver = memberService.findById(sendRequest.receiverId());

        Message message = new Message(sender, receiver, sendRequest.content(), false);

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

    // 나와 메시지를 주고받은 사람 목록
    public List<Member> getChatParticipants() {
        Member loggedInMember = memberService.findMember();

        // 발신자 또는 수신자로 등장한 메시지의 관련 회원 조회
        List<Message> messages = messageRepository.findBySenderIdOrReceiverIdOrderByCreatedAt(
                loggedInMember.getId(), loggedInMember.getId());

        // 발신자와 수신자 중 나 자신을 제외한 고유 회원 목록 반환
        return messages.stream()
                .map(message -> message.getSender().getId().equals(loggedInMember.getId())
                        ? message.getReceiver()
                        : message.getSender())
                .distinct()
                .toList();
    }
}
