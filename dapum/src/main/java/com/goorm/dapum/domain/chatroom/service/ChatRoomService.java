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

    // 채팅방 생성 기존에 있으면 가져오기
    public ChatRoomResponse findOrCreateChatRoom(Id id) {
        Member member1 = memberService.findMember();

        // CarePost가 있을 경우 CarePost로 채팅방 생성, 없으면 Post로 채팅방 생성
        Long carePostId = id.carePostId();
        Long postId = id.postId();

        ChatRoom chatRoom;

        // carePostId가 0이 아닌 경우 CarePost 기반 채팅방 생성
        if (carePostId != null && carePostId != 0) {
            CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
            chatRoom = chatRoomRepository.findByCarePostId(carePostId)
                    .orElseGet(() -> {
                        ChatRoom newChatRoom = new ChatRoom();
                        newChatRoom.setMember1(member1);
                        newChatRoom.setCarePost(carePost);
                        return chatRoomRepository.save(newChatRoom);
                    });
        }
        // carePostId가 0인 경우 Post 기반 채팅방 생성
        else if (postId != null && postId != 0) {
            Post post = postRepository.findById(postId).orElse(null);
            chatRoom = chatRoomRepository.findByPostId(postId)
                    .orElseGet(() -> {
                        ChatRoom newChatRoom = new ChatRoom();
                        newChatRoom.setMember1(member1);
                        newChatRoom.setPost(post);
                        return chatRoomRepository.save(newChatRoom);
                    });
        }
        else {
            throw new IllegalArgumentException("Invalid Post or CarePost ID");
        }

        // 메시지 내용 가져오기
        messageService.markMessagesAsRead(chatRoom.getId(), member1);
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoom.getId());

        // ChatRoomResponse에 채팅방 정보와 메시지 내용 포함
        return ChatRoomResponse.from(chatRoom, messages.stream()
                .map(MessageResponse::new)
                .toList(), member1);  // from 메서드를 사용하여 ChatRoomResponse 생성
    }

    // 채팅 목록 가져오기
    public List<ChatRoomList> getChatRooms() {
        Member member = memberService.findMember();
        List<ChatRoom> chatRooms = chatRoomRepository.findByMember(member);

        return chatRooms.stream()
                .map(chatRoom -> ChatRoomList.from(chatRoom, member))  // ChatRoomList.from() 호출
                .toList();
    }
}
