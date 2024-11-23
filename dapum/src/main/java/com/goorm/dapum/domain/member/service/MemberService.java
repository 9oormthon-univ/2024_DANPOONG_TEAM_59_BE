package com.goorm.dapum.domain.member.service;

import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.member.dto.MemberRequest;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public void create(MemberRequest request) {
        memberRepository.save(new Member(request));
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void updateNickname(Nickname nickname) {
        Member member = findMember();
        member.updateNickname(nickname);
        memberRepository.save(member);
    }

    public Member findMember() {
        String email = (String) RequestContextHolder.getRequestAttributes().getAttribute("email", RequestAttributes.SCOPE_REQUEST);
        if (email == null) {
            return null;
        }
        return memberRepository.findByEmail(email);
    }
}
