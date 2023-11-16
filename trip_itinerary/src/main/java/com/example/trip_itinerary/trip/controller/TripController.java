package com.example.trip_itinerary.trip.controller;


import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.request.TripUpdateRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @Operation(summary = "여행 저장", description = "여행 정보를 저장합니다.")
    @PostMapping
    public ResponseEntity<HttpStatus> saveTrip(@RequestBody @Validated TripSaveRequest tripSaveRequest, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        tripService.saveTrip(tripSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips")).build();
    }

    @Operation(summary = "전체 여행 리스트 조회", description = "전체 여행 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TripListFindResponse>> getAllTrips() {
        List<TripListFindResponse> trips = tripService.findAllTrips();

        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "여행 단건 조회", description = "입력한 여행 ID에 해당하는 여행 정보를 조회합니다.")
    @GetMapping("/{trip_id}")
    public ResponseEntity<TripFindResponse> getTripById(
            @Parameter(description = "조회할 여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId
    ) {
        TripFindResponse trip = tripService.getTripById(tripId);

        return ResponseEntity.ok(trip);
    }

    @Operation(summary = "여행 정보 수정", description = "입력한 여행 ID에 해당하는 여행 정보를 수정합니다.")
    @PatchMapping("/{trip_id}")
    public ResponseEntity<HttpStatus> updateTripById(
            @Parameter(description = "여행 정보를 수정할 여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody @Validated TripUpdateRequest tripUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        tripService.updateTrip(tripId, tripUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "여행 검색", description = "입력한 키워드를 통해 여행을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<TripListFindResponse>> searchTripByName(
            @Parameter(description = "검색할 키워드", required = true, example = "서울")
            @RequestParam("trip_name") String tripName
    ) {
        List<TripListFindResponse> tripList = tripService.searchTrip(tripName);

        return ResponseEntity.ok(tripList);
    }

    @Operation(summary = "사용자가 좋아요 표시한 여행 리스트 조회", description = "사용자가 좋아요 표시한 여행 리스트를 조회합니다.")
    @GetMapping("/like")
    public ResponseEntity<List<TripListFindResponse>> findLikeTrip(@AuthenticationPrincipal MemberAdapter memberAdapter) {
        List<TripListFindResponse> tripList = tripService.getLikeTripList(memberAdapter);

        return ResponseEntity.ok(tripList);
    }

}
