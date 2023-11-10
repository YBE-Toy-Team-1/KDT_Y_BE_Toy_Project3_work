package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
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

    @PostMapping("/{trip_id}/itinerary")
    public Long saveItinerary(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated ItinerarySaveRequest staySaveRequest) {
        return itineraryService.saveItinerary(id, staySaveRequest);
    }

    @PatchMapping("/itineraries/{itinerary_id}")
    public Long patchItinerary(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated ItineraryPatchRequest itineraryPatchRequest) {
        return itineraryService.patchItinerary(id, itineraryPatchRequest);
    }

}
