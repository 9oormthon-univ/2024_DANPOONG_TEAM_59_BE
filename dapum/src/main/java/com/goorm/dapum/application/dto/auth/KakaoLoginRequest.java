package com.goorm.dapum.application.dto.auth;

public record KakaoLoginRequest(
        Long id,
        String connected_at,
        String email,
        String nickname,
        String profile_image_url
) {
}
