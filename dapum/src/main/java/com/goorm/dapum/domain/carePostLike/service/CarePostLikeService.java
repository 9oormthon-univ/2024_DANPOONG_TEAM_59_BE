package com.goorm.dapum.domain.carePostLike.service;

import com.goorm.dapum.domain.carePost.entity.CarePost;
import com.goorm.dapum.domain.carePost.repository.CarePostRepository;
import com.goorm.dapum.domain.carePostLike.entity.CarePostLike;
import com.goorm.dapum.domain.carePostLike.repository.CarePostLikeRepository;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarePostLikeService {

    @Autowired
    private CarePostLikeRepository carePostLikeRepository;

    @Autowired
    private CarePostRepository carePostRepository;

    @Autowired
    private MemberService memberService;

    // 좋아요 개수 가져오기
    public long getLikeCount(Long carePostId) {
        CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
        return carePostLikeRepository.countByCarePostAndStatus(carePost, true); // true: 좋아요 개수만 카운트
    }

    // 좋아요/취소 토글
    @Transactional
    public void toggleLike(Long carePostId) {
        CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
        Member member = memberService.findMember();

        CarePostLike existingLike = carePostLikeRepository.findByCarePostAndMember(carePost, member)
                .orElse(null);

        if (existingLike != null) {
            // 기존 기록이 있으면 상태를 반전시킴
            existingLike.updateStatus(!existingLike.getStatus());
        } else {
            // 기존 기록이 없으면 새로 추가
            CarePostLike newLike = new CarePostLike(carePost, member, true);
            carePostLikeRepository.save(newLike);
        }
    }

    // 특정 사용자가 좋아요를 눌렀는지 확인
    public boolean isLiked(Long carePostId) {
        CarePost carePost = carePostRepository.findById(carePostId).orElse(null);
        Member member = memberService.findMember();

        CarePostLike existingLike = carePostLikeRepository.findByCarePostAndMember(carePost, member)
                .orElse(null);

        // 좋아요 상태가 true이면 true 반환, 아니면 false 반환
        return existingLike != null && existingLike.getStatus();
    }
}

