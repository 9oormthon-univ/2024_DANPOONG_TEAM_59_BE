package com.goorm.dapum.application.controller.message;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.message.dto.SendRequest;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // 메시지 전송
    @PostMapping("/send")
    @Operation(summary = "메시지 전송")
    public ResponseEntity<Void> sendMessage(@RequestBody SendRequest sendRequest) {
        messageService.sendMessage(sendRequest);
        return ResponseEntity.ok().build();
    }

    // 대화 내역 조회
    @GetMapping("/conversation/{memberId}")
    @Operation(summary = "대화 내역 조회")
    public ResponseEntity<List<Message>> getConversation(@PathVariable Long memberId) {
        List<Message> conversation = messageService.getConversation(memberId);
        return ResponseEntity.ok(conversation);
    }

    // 읽지 않은 메시지 수 조회
    @GetMapping("/unread/count")
    @Operation(summary = "읽지 않은 메시지 수 조회")
    public ResponseEntity<Long> getUnreadMessageCount() {
        Long unreadCount = messageService.getUnreadMessageCount();
        return ResponseEntity.ok(unreadCount);
    }

    // 메시지 읽음 처리
    @PostMapping("/read/{senderId}")
    @Operation(summary = "메시지 읽음 처리")
    public ResponseEntity<Void> markMessagesAsRead(@PathVariable Long senderId) {
        messageService.markMessagesAsRead(senderId);
        return ResponseEntity.ok().build();
    }

    // 메시지를 주고받은 사람 목록 조회
    @GetMapping("/participants")
    @Operation(summary = "메시지를 주고받은 사람 목록 조회")
    public ResponseEntity<List<Member>> getChatParticipants() {
        List<Member> participants = messageService.getChatParticipants();
        return ResponseEntity.ok(participants);
    }
}

