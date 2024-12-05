package com.goorm.dapum.domain.chatroom.service;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
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

    // 채팅방 생성 기존에 있으면 가져오기
    public ChatRoom findOrCreateChatRoom(Member member1, Member member2) {
        return chatRoomRepository.findByMembers(member1, member2)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setMember1(member1);
                    newChatRoom.setMember2(member2);
                    return chatRoomRepository.save(newChatRoom);
                });
    }

    // 채팅 목록 가져오기
    public List<ChatRoom> getChatRooms(Member member) {
        return chatRoomRepository.findByMember(member);
    }
}
