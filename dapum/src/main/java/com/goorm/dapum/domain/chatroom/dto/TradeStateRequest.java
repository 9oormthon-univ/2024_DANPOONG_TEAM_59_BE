package com.goorm.dapum.domain.chatroom.dto;

public record TradeStateRequest(
        Long chatRoomId,
        String tradeState
) {
}
