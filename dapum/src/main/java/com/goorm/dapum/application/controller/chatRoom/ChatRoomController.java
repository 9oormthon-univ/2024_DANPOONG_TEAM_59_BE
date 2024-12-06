package com.goorm.dapum.application.controller.chatRoom;

import com.goorm.dapum.domain.chatroom.dto.ChatRoomList;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomRequest;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomResponse;
import com.goorm.dapum.domain.chatroom.entity.TradeStateRequest;
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

    @PostMapping("{care_post_id}")
    @Operation(summary = "돌봄 게시글과 관련된 채팅 불러오기 (없으면 생성)")
    public ResponseEntity<ChatRoomResponse> findOrCreateCareChatRoom(@RequestBody ChatRoomRequest request) {
        ChatRoomResponse chatRoomResponse = chatRoomService.findOrCreateChatRoom(request);
        return ResponseEntity.ok(chatRoomResponse);
    }

    @GetMapping
    @Operation(summary = "채팅 목록 불러오기")
    public ResponseEntity<List<ChatRoomList>> getChatRooms() {
        List<ChatRoomList> chatRooms = chatRoomService.getChatRooms();
        return ResponseEntity.ok(chatRooms);
    }

    @PutMapping("/trade-state")
    @Operation(summary = "거래 상태 변경")
    public ResponseEntity<Void> changeTradeState(@RequestBody TradeStateRequest request) {
        try {
            chatRoomService.changeTradeState(request);
            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).build(); // 권한 없는 사용자
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 잘못된 요청
        }
    }
}


