package com.goorm.dapum.domain.chatroom.repository;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
    @Query("SELECT c FROM ChatRoom c WHERE (c.member1 = :member1 AND c.member2 = :member2) OR (c.member1 = :member2 AND c.member2 = :member1)")
    Optional<ChatRoom> findByMembers(@Param("member1") Member member1, @Param("member2") Member member2);


    @Query("SELECT c FROM ChatRoom c WHERE c.member1 = :member OR c.member2 = :member")
    List<ChatRoom> findByMember(@Param("member") Member member);

}
