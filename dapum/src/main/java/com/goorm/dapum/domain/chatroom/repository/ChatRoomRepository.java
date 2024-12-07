package com.goorm.dapum.domain.chatroom.repository;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.chatroom.entity.TradeState;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
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

    Optional<ChatRoom> findByCarePostId(Long carePostId);

    Optional<ChatRoom> findByPostId(Long postId);

    @Query("SELECT c FROM ChatRoom c WHERE c.tradeState = :tradeState AND (c.member1 = :member OR c.member2 = :member)")
    List<ChatRoom> findByTradeStateAndMember(@Param("tradeState") TradeState tradeState, @Param("member") Member member);

    @Query("SELECT c FROM ChatRoom c " +
            "WHERE c.carePost = :carePost " +
            "AND ((c.member1 = :currentUser AND c.member2 = :otherMember) " +
            "  OR (c.member1 = :otherMember AND c.member2 = :currentUser))")
    Optional<ChatRoom> findByCarePostAndMembers(
            @Param("carePost") CarePost carePost,
            @Param("currentUser") Member currentUser,
            @Param("otherMember") Member otherMember);


    @Query("SELECT c FROM ChatRoom c " +
            "WHERE c.post = :post " +
            "AND ((c.member1 = :currentUser AND c.member2 = :otherMember) " +
            "  OR (c.member1 = :otherMember AND c.member2 = :currentUser))")
    Optional<ChatRoom> findByPostAndMembers(
            @Param("post") Post post,
            @Param("currentUser") Member currentUser,
            @Param("otherMember") Member otherMember);

}
