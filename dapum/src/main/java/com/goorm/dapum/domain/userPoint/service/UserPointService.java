package com.goorm.dapum.domain.userPoint.service;

import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.userPoint.entity.UserPoint;
import com.goorm.dapum.domain.userPoint.repository.UserPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPointService {
    @Autowired
    private final UserPointRepository userPointRepository;

    public void createInitialPoint(Member member) {
        UserPoint userPoint = UserPoint.builder()
                .member(member)
                .points(100) // 초기 포인트 설정
                .build();
        userPointRepository.save(userPoint);
    }
}
