package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;

import java.util.List;

public interface TripService {

    Long saveTrip(TripSaveRequest tripSaveRequest);

    List<TripListFindResponse> findAllTrips();

    TripFindResponse getTripById(Long id);

    Long updateTrip(Long id, TripPatchRequest tripPatchRequest);

}
