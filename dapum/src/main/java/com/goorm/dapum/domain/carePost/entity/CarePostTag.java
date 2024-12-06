package com.goorm.dapum.domain.carePost.entity;

public enum CarePostTag {
    RECRUITING("구인중"),   // 구인중
    COMPLETED("구인완료"),    // 구인완료
    RESERVED("예약중");      // 예약중

    private final String displayName;  // 표시할 이름

    // 생성자
    CarePostTag(String displayName) {
        this.displayName = displayName;
    }

    // 표시 이름 반환 메서드
    public String getDisplayName() {
        return this.displayName;
    }

    // 문자열을 받아 해당하는 Tag 반환
    public static CarePostTag fromDisplayName(String displayName) {
        for (CarePostTag carePostTag : CarePostTag.values()) {
            if (carePostTag.getDisplayName().equals(displayName)) {
                return carePostTag;
            }
        }
        throw new IllegalArgumentException("Unknown tag: " + displayName);
    }
}

