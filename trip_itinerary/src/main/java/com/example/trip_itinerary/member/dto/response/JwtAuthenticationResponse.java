package com.example.trip_itinerary.member.dto.response;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {

    private final String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
