package com.example.trip_itinerary.itinerary.dto.response;

import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItineraryErrorResponse {

    private String errorCode;
    private String message;
    private int status;

    public static ItineraryErrorResponse from(ItineraryErrorCode itineraryErrorCode) {
        return ItineraryErrorResponse.builder()
                .errorCode(itineraryErrorCode.name())
                .message(itineraryErrorCode.getMessage())
                .status(itineraryErrorCode.getStatus())
                .build();
    }

}
