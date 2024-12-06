package com.goorm.dapum.domain.chatroom.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomList;
import com.goorm.dapum.domain.chatroom.dto.ChatRoomResponse;
import com.goorm.dapum.domain.chatroom.dto.Id;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.repository.ChatRoomRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.message.dto.MessageResponse;
import com.goorm.dapum.domain.message.entity.Message;
import com.goorm.dapum.domain.message.repository.MessageRepository;
import com.goorm.dapum.domain.message.service.MessageService;
import com.goorm.dapum.domain.post.entity.Post;
import com.goorm.dapum.domain.post.repository.PostRepository;
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
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final CarePostRepository carePostRepository;
    @Autowired
    private final PostRepository postRepository;

    // CarePost 기반 채팅방 생성 또는 가져오기
    public ChatRoomResponse findOrCreateCareChatRoom(Long carePostId) {
        Member member1 = memberService.findMember();

        CarePost carePost = carePostRepository.findById(carePostId)
                .orElseThrow(() -> new IllegalArgumentException("CarePost가 존재하지 않습니다."));

        ChatRoom chatRoom = chatRoomRepository.findByCarePostId(carePostId)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setMember1(member1);
                    newChatRoom.setMember2(carePost.getMember());
                    newChatRoom.setCarePost(carePost);
                    return chatRoomRepository.save(newChatRoom);
                });

        // 메시지 가져오기 및 읽음 처리
        messageService.markMessagesAsRead(chatRoom.getId(), member1);
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoom.getId());

        // ChatRoomResponse 생성
        return ChatRoomResponse.from(chatRoom, messages.stream()
                .map(MessageResponse::new)
                .toList(), member1, false, false);
    }

    // 채팅 목록 가져오기
    public List<ChatRoomList> getChatRooms() {
        Member member = memberService.findMember();
        List<ChatRoom> chatRooms = chatRoomRepository.findByMember(member);

        return chatRooms.stream()
                .map(chatRoom -> ChatRoomList.from(chatRoom, member)) // ChatRoomList 생성
                .toList();
    }
}
