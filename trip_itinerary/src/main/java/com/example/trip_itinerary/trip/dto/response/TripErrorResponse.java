package com.example.trip_itinerary.trip.dto.response;

import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripErrorResponse {

    private String errorCode;
    private String message;
    private int status;

    public static TripErrorResponse from(TripErrorCode tripErrorCode) {
        return TripErrorResponse.builder()
                .errorCode(tripErrorCode.name())
                .message(tripErrorCode.getMessage())
                .status(tripErrorCode.getStatus())
                .build();
    }

}
