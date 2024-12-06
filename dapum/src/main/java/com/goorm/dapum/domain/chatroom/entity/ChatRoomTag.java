package com.goorm.dapum.domain.chatroom.entity;

public enum ChatRoomTag {
    CARE("돌봄"),  // 돌봄
    SHARE("나눔"); // 나눔

    private final String displayName;

    ChatRoomTag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // 문자열로부터 Enum 값 찾기
    public static ChatRoomTag fromDisplayName(String displayName) {
        for (ChatRoomTag tag : ChatRoomTag.values()) {
            if (tag.getDisplayName().equals(displayName)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("Unknown ChatRoomTag: " + displayName);
    }
}
