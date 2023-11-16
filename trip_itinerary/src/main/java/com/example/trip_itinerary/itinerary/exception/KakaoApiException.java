package com.example.trip_itinerary.itinerary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoApiException extends RuntimeException {
    private ItineraryErrorCode errorCode;
}
