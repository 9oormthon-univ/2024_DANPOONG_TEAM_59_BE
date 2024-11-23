package com.goorm.dapum.domain.post.entity;

public enum Tag {
    ADVICE("조언"),
    SHARE("나눔"),
    INFORMATION("정보");

    private final String displayName;

    Tag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
