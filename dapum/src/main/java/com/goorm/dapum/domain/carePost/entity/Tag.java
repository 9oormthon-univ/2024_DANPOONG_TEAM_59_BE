package com.goorm.dapum.domain.carePost.entity;

public enum Tag {
    RECRUITING("구인중"),   // 구인중
    COMPLETED("구인완료"),    // 구인완료
    RESERVED("예약중");      // 예약중

    private final String displayName;  // 표시할 이름

    // 생성자
    Tag(String displayName) {
        this.displayName = displayName;
    }

    // 표시 이름 반환 메서드
    public String getDisplayName() {
        return this.displayName;
    }

    // 문자열을 받아 해당하는 Tag 반환
    public static Tag fromDisplayName(String displayName) {
        for (Tag tag : Tag.values()) {
            if (tag.getDisplayName().equals(displayName)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("Unknown tag: " + displayName);
    }
}

