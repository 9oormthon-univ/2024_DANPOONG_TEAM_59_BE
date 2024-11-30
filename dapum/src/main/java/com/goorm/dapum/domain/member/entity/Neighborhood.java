package com.goorm.dapum.domain.member.entity;

import com.goorm.dapum.application.dto.member.NeighborhoodRequest;
import jakarta.persistence.Embeddable;

@Embeddable
public class Neighborhood {
    private String province;
    private String city;
    private String district;

    // Default constructor
    public Neighborhood() {}

    public Neighborhood(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public Neighborhood(NeighborhoodRequest neighborhoodRequest) {
        this.province = neighborhoodRequest.province();
        this.city = neighborhoodRequest.city();
        this.district = neighborhoodRequest.district();
    }
    // Getters and Setters
}

