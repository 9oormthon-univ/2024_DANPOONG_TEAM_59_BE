package com.goorm.dapum.domain.auth.service;


import com.goorm.dapum.application.dto.auth.KakaoLoginRequest;
import com.goorm.dapum.application.dto.auth.KakaoLoginResponse;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.entity.Status;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.infrastructure.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoAuthService {
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final MemberService memberService;

    public KakaoLoginResponse login(KakaoLoginRequest request) {
        if(!memberService.existsByEmail(request.email())){
            String nickname = "새가입자" + request.id();
            memberService.create(new MemberRequest(request.email(), request.id(), request.nickname(), nickname, request.profile_image_url()));
        }
        Member member = memberService.findByEmail(request.email());
        if(member.getStatus() == Status.DELETED)
            memberService.updateStatus(member, Status.ACTIVE);

        return new KakaoLoginResponse(jwtService.generateToken(request.email()));
    }
}
