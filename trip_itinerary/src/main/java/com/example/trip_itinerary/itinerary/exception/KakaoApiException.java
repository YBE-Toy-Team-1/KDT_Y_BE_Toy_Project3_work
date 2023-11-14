package com.example.trip_itinerary.itinerary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoApiException extends RuntimeException {
    private ItineraryErrorCode errorCode;
}
