package com.goorm.dapum.domain.chatroom.entity;

public record TradeStateRequest(
        Long chatRoomId,
        String tradeState
) {
}
