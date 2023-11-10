package com.example.trip_itinerary.trip.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidDateFormatException extends RuntimeException {

    private TripErrorCode errorCode;

}
