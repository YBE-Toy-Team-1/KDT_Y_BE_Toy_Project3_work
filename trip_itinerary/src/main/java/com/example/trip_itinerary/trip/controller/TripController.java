package com.example.trip_itinerary.trip.controller;


import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public Long saveTrip(@RequestBody @Validated TripSaveRequest tripSaveRequest) {
        return tripService.saveTrip(tripSaveRequest);
    }

    @GetMapping
    public List<TripListFindResponse> getAllTrips() {
        return tripService.findAllTrips();
    }

    @GetMapping("/{id}")
    public TripFindResponse getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PatchMapping("/{id}")
    public Long updateTripById(@PathVariable Long id, @RequestBody @Validated TripPatchRequest tripPatchRequest) {
        return tripService.updateTrip(id, tripPatchRequest);
    }

}
