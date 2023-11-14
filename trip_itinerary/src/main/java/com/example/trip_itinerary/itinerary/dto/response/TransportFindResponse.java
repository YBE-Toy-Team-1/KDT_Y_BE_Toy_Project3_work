package com.example.trip_itinerary.itinerary.dto.response;

import com.example.trip_itinerary.itinerary.domain.Itinerary;
import lombok.Builder;

@Builder
public class TransportFindResponse extends ItineraryFindResponse{

    public static ItineraryFindResponse fromEntity(Itinerary itinerary) {
        return null;
    }
}
