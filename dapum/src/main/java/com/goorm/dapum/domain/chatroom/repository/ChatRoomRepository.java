package com.goorm.dapum.domain.chatroom.repository;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMembers(Member member1, Member member2);

    @Query("SELECT c FROM ChatRoom c WHERE c.member1 = :member OR c.member2 = :member")
    List<ChatRoom> findByMember(@Param("member") Member member);

}
