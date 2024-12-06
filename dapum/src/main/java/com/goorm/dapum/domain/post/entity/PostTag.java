package com.goorm.dapum.domain.post.entity;

public enum PostTag {
    ADVICE("조언"),
    SHARE("나눔"),
    INFORMATION("정보"),
    COMPLETED_SHARE("나눔완료");

    private final String displayName;

    PostTag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PostTag fromDisplayName(String displayName) {
        for (PostTag tag : PostTag.values()) {
            if (tag.getDisplayName().equals(displayName)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("Unknown PostTag: " + displayName);
    }
}
