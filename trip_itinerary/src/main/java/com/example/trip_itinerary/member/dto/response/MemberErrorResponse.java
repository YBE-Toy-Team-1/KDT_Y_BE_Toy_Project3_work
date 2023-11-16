package com.example.trip_itinerary.member.dto.response;

import com.example.trip_itinerary.member.exception.MemberErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberErrorResponse {

    private String errorCode;
    private String message;
    private int status;

    @Builder
    public MemberErrorResponse(String errorCode, String message, int status) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    public static MemberErrorResponse from(MemberErrorCode memberErrorCode) {
        return MemberErrorResponse.builder()
                .errorCode(memberErrorCode.name())
                .message(memberErrorCode.getMessage())
                .status(memberErrorCode.getStatus())
                .build();
    }

}
