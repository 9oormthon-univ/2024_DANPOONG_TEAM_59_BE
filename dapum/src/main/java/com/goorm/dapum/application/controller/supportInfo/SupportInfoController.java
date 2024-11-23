package com.goorm.dapum.application.controller.supportInfo;

import com.goorm.dapum.domain.supportInfo.dto.SupportDetail;
import com.goorm.dapum.domain.supportInfo.dto.SupportInfoList;
import com.goorm.dapum.domain.supportInfo.service.SupportInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-info")
@RequiredArgsConstructor
public class SupportInfoController {

    @Autowired
    private final SupportInfoService supportInfoService;

    @GetMapping
    @Operation(summary = "모든 지원 정보 가져오기")
    public ResponseEntity<List<SupportInfoList>> getSupportInfoList() {
        List<SupportInfoList> supportInfoList = supportInfoService.getSupportInfoList();
        return ResponseEntity.ok(supportInfoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 지원 정보 자세히 보기")
    public ResponseEntity<SupportDetail> getSupportDetail(@PathVariable Long id) {
        SupportDetail supportDetail = supportInfoService.getSupportDetail(id);
        return ResponseEntity.ok(supportDetail);
    }
}
