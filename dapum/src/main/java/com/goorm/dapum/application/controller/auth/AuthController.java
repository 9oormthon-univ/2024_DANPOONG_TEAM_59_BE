package com.goorm.dapum.application.controller.auth;

import com.goorm.dapum.application.dto.auth.KakaoLoginRequest;
import com.goorm.dapum.application.dto.auth.KakaoLoginResponse;
import com.goorm.dapum.domain.auth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/kakao/callback")
    @Operation(summary = "카카오 로그인")
    public KakaoLoginResponse kakaoLogin(@RequestBody KakaoLoginRequest request) {
        return kakaoAuthService.login(request);
    }
}

