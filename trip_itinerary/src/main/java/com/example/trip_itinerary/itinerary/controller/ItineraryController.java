package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;
import com.example.trip_itinerary.itinerary.service.ItineraryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping("/{id}/itinerary")
    public Long saveItinerary(@PathVariable Long id, @RequestBody @Validated ItinerarySaveRequest staySaveRequest) {
        return itineraryService.saveItinerary(id, staySaveRequest);
    }

    @PatchMapping("/itineraries/{id}")
    public Long patchItinerary(@PathVariable Long id, @RequestBody @Validated ItineraryPatchRequest itineraryPatchRequest) {
        return itineraryService.patchItinerary(id, itineraryPatchRequest);
    }

}
