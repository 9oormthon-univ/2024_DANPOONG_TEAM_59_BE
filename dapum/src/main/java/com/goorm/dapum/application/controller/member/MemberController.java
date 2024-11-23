package com.goorm.dapum.application.controller.member;

import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PutMapping("/nickname")
    @Operation(summary = "사용자 닉네임 입력")
    public ResponseEntity<?> update(@RequestBody Nickname nickname) {
        memberService.updateNickname(nickname);
        return ResponseEntity.ok().build();
    }
}
