package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportPatchRequest;
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

    @PostMapping("/{trip_id}/transport")
    public ResponseEntity<Void> saveTransport(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated TransportSaveRequest transportSaveRequest) {
        itineraryService.saveTransport(id, transportSaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PostMapping("/{trip_id}/stay")
    public ResponseEntity<Void> saveTransport(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated StaySaveRequest staySaveRequest) {
        itineraryService.saveStay(id, staySaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PostMapping("/{trip_id}/accommodation")
    public ResponseEntity<Void> saveAccommodation(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated AccommodationSaveRequest accommodationSaveRequest) {
        itineraryService.saveAccommodation(id, accommodationSaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PatchMapping("/{trip_id}/transport/{itinerary_id}")
    public ResponseEntity<Void> patchTransport(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated TransportPatchRequest transportPatchRequest) {
        Long tripId = itineraryService.patchTransport(id, transportPatchRequest);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }
    @PatchMapping("/{trip_id}/stay/{itinerary_id}")
    public ResponseEntity<Void> patchStay(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated StayPatchRequest stayPatchRequest) {
        Long tripId = itineraryService.patchStay(id, stayPatchRequest);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }
    @PatchMapping("/{trip_id}/accommodation/{itinerary_id}")
    public ResponseEntity<Void> patchAccommodation(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated AccommodationPatchRequest accommodationPatchRequest) {
        Long tripId = itineraryService.patchAccommodation(id, accommodationPatchRequest);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }



}
