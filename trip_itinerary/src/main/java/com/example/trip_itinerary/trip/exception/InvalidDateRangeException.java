package com.example.trip_itinerary.trip.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidDateRangeException extends RuntimeException {

    private TripErrorCode errorCode;

}
