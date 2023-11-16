package com.example.trip_itinerary.trip.controller;


import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.trip.dto.request.TripUpdateRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trips")
@Slf4j
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @Operation(summary = "여행 저장")
    @PostMapping
    public ResponseEntity<HttpStatus> saveTrip(@RequestBody @Validated TripSaveRequest tripSaveRequest, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        tripService.saveTrip(tripSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips")).build();
    }

    @Operation(summary = "전체 여행 리스트 조회")
    @GetMapping
    public ResponseEntity<List<TripListFindResponse>> getAllTrips() {
        log.error("here");
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
    public ResponseEntity<HttpStatus> updateTripById(
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody @Validated TripUpdateRequest tripUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter) {

        tripService.updateTrip(tripId, tripUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "여행 검색")
    @GetMapping("/search")
    public ResponseEntity<List<TripListFindResponse>> searchTripByName(@RequestParam("trip_name") String tripName){
        List<TripListFindResponse> tripList = tripService.searchTrip(tripName);

        return ResponseEntity.ok(tripList);
    }

    @GetMapping("/like") // 1. trips/like 2. trips/likes 3.trips/mylikes 3. trips/likedtrips 4. trips/liked
    public ResponseEntity<List<TripListFindResponse>> findLikeTrip(@AuthenticationPrincipal MemberAdapter memberAdapter){
        List<TripListFindResponse> tripList = tripService.getLikeTripList(memberAdapter);
        return ResponseEntity.ok(tripList);
    }

}
