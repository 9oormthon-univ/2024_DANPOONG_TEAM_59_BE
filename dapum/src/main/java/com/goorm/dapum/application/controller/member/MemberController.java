package com.goorm.dapum.application.controller.member;

import com.goorm.dapum.application.dto.member.NeighborhoodRequest;
import com.goorm.dapum.application.dto.member.Nickname;
import com.goorm.dapum.domain.carePost.dto.CarePostListResponse;
import com.goorm.dapum.domain.member.dto.MemberResponse;
import com.goorm.dapum.domain.member.service.MemberService;
import com.goorm.dapum.domain.post.dto.PostListResponse;
import com.goorm.dapum.domain.postLike.dto.PostLikeList;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<?> updateNeighborhood(@RequestBody NeighborhoodRequest neighborhoodRequest) {
        memberService.updateNeighborhood(neighborhoodRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "좋아요 한 게시글 가져오기")
    public ResponseEntity<List<PostLikeList>> getLikePost(){
        List<PostLikeList> lists = memberService.getPostLikeList();
        return ResponseEntity.ok().body(lists);
    }

    @GetMapping("/posts")
    @Operation(summary = "내가 작성한 게시물 목록 조회")
    public ResponseEntity<List<PostListResponse>> getMyPosts() {
        List<PostListResponse> myPosts = memberService.getMyPosts();
        return ResponseEntity.ok(myPosts); // HTTP 200 상태와 게시물 목록 반환
    }

    @GetMapping("/care")
    @Operation(summary = "내가 작성한 돌봄 목록 가져오기")
    public ResponseEntity<List<CarePostListResponse>> getMyCarePosts() {
        List<CarePostListResponse> myCares = memberService.getMyCares();
        return ResponseEntity.ok().body(myCares);
    }

    @GetMapping("/takeCare")
    @Operation(summary = "내가 돌봐준 돌봄 목록 불러오기")
    public ResponseEntity<List<CarePostListResponse>> getMyTakeCarePosts() {
        List<CarePostListResponse> myCares = memberService.getMyTakeCares();
        return ResponseEntity.ok().body(myCares);
    }

    @GetMapping("/Info")
    @Operation(summary = "사용자 정보 가져오기")
    public ResponseEntity<MemberResponse> getMemberInfo() {
        MemberResponse memberInfo = memberService.getMemberInfo();
        return ResponseEntity.ok(memberInfo);
    }
}
