package com.goorm.dapum.application.controller.message;

import com.goorm.dapum.domain.message.dto.SendRequest;
import com.goorm.dapum.domain.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    private final MessageService messageService;

    @PostMapping("/{chatRoomId}")
    @Operation(summary = "메시지 전송")
    public ResponseEntity<Boolean> sendMessage(@PathVariable Long chatRoomId, @RequestBody SendRequest request) {
        boolean isSent = messageService.sendMessage(chatRoomId, request);
        return ResponseEntity.ok(isSent);
    }
}

