package com.goorm.dapum.application.dto.member;

public record NeighborhoodRequest(
        String province,  // 도 또는 특별시 (예: 경기도, 서울특별시)
        String city,      // 시 또는 군 (예: 용인시, 수원시)
        String district   // 동 또는 읍/면 (예: 고기동, 서초동)
) {
}
