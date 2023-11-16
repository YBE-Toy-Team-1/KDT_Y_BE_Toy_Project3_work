package com.example.trip_itinerary.trip.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidDateFormatException extends RuntimeException {

    private TripErrorCode errorCode;

}
