package com.goorm.dapum.domain.chatroom.repository;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMembers(Member member1, Member member2);

    List<ChatRoom> findByMember(Member member);
}
