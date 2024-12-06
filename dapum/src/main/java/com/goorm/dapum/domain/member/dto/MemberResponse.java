package com.goorm.dapum.domain.member.dto;

public record MemberResponse(
        Long memberId,
        String kakaoName,
        String nickName,
        String profileImageUrl,
        int points,
        Double temperature,
        String province,
        String city,
        String district
) {

}
