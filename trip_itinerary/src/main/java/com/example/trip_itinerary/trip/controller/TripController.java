package com.example.trip_itinerary.trip.controller;


import com.example.trip_itinerary.trip.dto.request.TripUpdateRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "여행 저장")
    @PostMapping
    public ResponseEntity<HttpStatus> saveTrip(@RequestBody @Validated TripSaveRequest tripSaveRequest) {
        tripService.saveTrip(tripSaveRequest);

        return ResponseEntity.created(URI.create("/trips")).build();
    }

    @Operation(summary = "전체 여행 리스트 조회")
    @GetMapping
    public ResponseEntity<List<TripListFindResponse>> getAllTrips() {
        List<TripListFindResponse> trips = tripService.findAllTrips();

        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "여행 단건 조회")
    @GetMapping("/{trip_id}")
    public ResponseEntity<TripFindResponse> getTripById(@PathVariable(name = "trip_id") Long tripId) {
        TripFindResponse trip = tripService.getTripById(tripId);

        return ResponseEntity.ok(trip);
    }

    @Operation(summary = "여행 정보 수정")
    @PatchMapping("/{trip_id}")
    public ResponseEntity<HttpStatus> updateTripById(@PathVariable(name = "trip_id") Long tripId, @RequestBody @Validated TripUpdateRequest tripUpdateRequest) {
        tripService.updateTrip(tripId, tripUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "여행 검색")
    @GetMapping({"search/{trip_name}"})
    public ResponseEntity<List<TripListFindResponse>> searchTripByName(@PathVariable String tripName){
        List<TripListFindResponse> tripList = tripService.searchTrip(tripName);

        return ResponseEntity.ok(tripList);
    }

}
