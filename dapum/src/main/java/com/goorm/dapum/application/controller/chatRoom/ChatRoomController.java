package com.goorm.dapum.application.controller.chatRoom;

import com.goorm.dapum.domain.chatroom.dto.ChatRoomList;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomResponse;
import com.goorm.dapum.domain.chatroom.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    @Autowired
    private final ChatRoomService chatRoomService;

    @PostMapping("/{member2Id}")
    @Operation(summary = "채팅 불러오기 (없으면 생성)")
    public ResponseEntity<ChatRoomResponse> findOrCreateChatRoom(@PathVariable Long member2Id) {
        ChatRoomResponse chatRoomResponse = chatRoomService.findOrCreateChatRoom(member2Id);
        return ResponseEntity.ok(chatRoomResponse);
    }

    @GetMapping
    @Operation(summary = "채팅 목록 불러오기")
    public ResponseEntity<List<ChatRoomList>> getChatRooms() {
        List<ChatRoomList> chatRooms = chatRoomService.getChatRooms();
        return ResponseEntity.ok(chatRooms);
    }
}


