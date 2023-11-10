package com.example.trip_itinerary.trip.controller;


import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveTrip(@RequestBody @Validated TripSaveRequest tripSaveRequest) {
        tripService.saveTrip(tripSaveRequest);

        return ResponseEntity.created(URI.create("/trips")).build();
    }

    @GetMapping
    public ResponseEntity<List<TripListFindResponse>> getAllTrips() {
        List<TripListFindResponse> trips = tripService.findAllTrips();

        return ResponseEntity.status(HttpStatus.OK).body(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripFindResponse> getTripById(@PathVariable Long id) {
        TripFindResponse trip = tripService.getTripById(id);

        return ResponseEntity.status(HttpStatus.OK).body(trip);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTripById(@PathVariable Long id, @RequestBody @Validated TripPatchRequest tripPatchRequest) {
        tripService.updateTrip(id, tripPatchRequest);

        return ResponseEntity.created(URI.create("/trips" + id)).build();
    }

}
