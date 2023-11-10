package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;

public interface ItineraryService {

    Long saveItinerary(Long id, ItinerarySaveRequest itinerarySaveRequest);

    Long patchItinerary(Long id, ItineraryPatchRequest itineraryPatchRequest);

}
