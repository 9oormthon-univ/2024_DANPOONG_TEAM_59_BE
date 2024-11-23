package com.goorm.dapum.domain.member.dto;

public record MemberRequest(
        String email,
        Long kakaoId,
        String kakaoName,
        String nickname,
        String profileImageUrl
) {
}
