package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;
import com.example.trip_itinerary.itinerary.service.ItineraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/trips")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping("/{trip_id}/itinerary")
    public ResponseEntity<Void> saveItinerary(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated ItinerarySaveRequest staySaveRequest) {
        itineraryService.saveItinerary(id, staySaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id + "/itinerary")).build();
    }

    @PatchMapping("/{trip_id}/itineraries/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchItinerary(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated ItineraryPatchRequest itineraryPatchRequest) {
        itineraryService.patchItinerary(id, itineraryPatchRequest);

        return ResponseEntity.created(URI.create("/trips/" + id + "/itineraries/" + id)).build(); // TODO : id부분 tripId, itineraryId로 변경해야 함
    }

}
