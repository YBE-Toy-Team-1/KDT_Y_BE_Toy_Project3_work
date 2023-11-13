package com.example.trip_itinerary.member.dto.response;

public class ApiResponse {

    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
