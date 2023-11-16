package com.example.trip_itinerary.itinerary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidDateTimeRangeException extends RuntimeException {

    private ItineraryErrorCode errorCode;

}
