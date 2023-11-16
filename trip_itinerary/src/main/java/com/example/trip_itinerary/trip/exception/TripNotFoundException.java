package com.example.trip_itinerary.trip.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripNotFoundException extends RuntimeException {

    private TripErrorCode errorCode;

}
