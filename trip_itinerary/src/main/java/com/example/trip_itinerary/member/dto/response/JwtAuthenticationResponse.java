package com.example.trip_itinerary.member.dto.response;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {

    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
