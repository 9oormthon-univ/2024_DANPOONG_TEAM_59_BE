package com.goorm.dapum.application.controller.member;

import com.goorm.dapum.application.dto.member.Neighborhood;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.postLike.dto.PostLikeList;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PutMapping("/nickname")
    @Operation(summary = "사용자 닉네임 입력")
    public ResponseEntity<?> updateNickname(@RequestBody Nickname nickname) {
        memberService.updateNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/neighborhood")
    @Operation(summary = "동네 입력")
    public ResponseEntity<?> updateNeighborhood(@RequestBody Neighborhood neighborhood) {
        memberService.updateNeighborhood(neighborhood);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "좋아요 한 게시글 가져오기")
    public ResponseEntity<List<PostLikeList>> getLikePost(){
        List<PostLikeList> lists = memberService.getPostLikeList();
        return ResponseEntity.ok().body(lists);
    }
}
