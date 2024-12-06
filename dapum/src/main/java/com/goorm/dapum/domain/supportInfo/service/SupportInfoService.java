package com.goorm.dapum.domain.supportInfo.service;

import com.goorm.dapum.domain.supportInfo.dto.SupportDetail;
import com.goorm.dapum.domain.supportInfo.dto.SupportInfoList;
import com.goorm.dapum.domain.supportInfo.entity.SupportInfo;
import com.goorm.dapum.domain.supportInfo.repository.SupportInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SupportInfoService {
    @Autowired
    private SupportInfoRepository supportInfoRepository;

    public List<SupportInfoList> getSupportInfoList() {
        return supportInfoRepository.findAll()
                .stream()
                .map(supportInfo -> new SupportInfoList(
                        supportInfo.getId(),
                        supportInfo.getTags(),
                        supportInfo.getTitle(),
                        supportInfo.getDepartment(),
                        supportInfo.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public SupportDetail getSupportDetail(Long id) {
        SupportInfo supportInfo = supportInfoRepository.findById(id)
                .orElse(null);
        return Objects.requireNonNull(supportInfo).detail();
    }
}
