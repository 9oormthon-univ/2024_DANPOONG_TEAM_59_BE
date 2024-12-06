package com.goorm.dapum.domain.chatroom.entity;

import lombok.Getter;

@Getter
public enum TradeState {
    DISCUSSION("대화"),    // 거래가 없는 상태
    RESERVED("예약"),         // 예약 상태
    TRADE_COMPLETED("거래완료"); // 거래 완료 상태

    private final String displayName;

    TradeState(String displayName) {
        this.displayName = displayName;
    }

    // 문자열로부터 Enum 값을 찾는 메서드
    public static TradeState fromDisplayName(String displayName) {
        for (TradeState state : TradeState.values()) {
            if (state.getDisplayName().equals(displayName)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown TradeState: " + displayName);
    }
}

