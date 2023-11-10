package com.example.trip_itinerary.trip.dto.response;


import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.itinerary.dto.response.ItineraryFindResponse;
import com.example.trip_itinerary.trip.domain.Trip;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripFindResponse {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonProperty("is_domestic")
    private boolean isDomestic;

    private List<ItineraryFindResponse> itineraryList;

    public static TripFindResponse fromEntity(Trip trip) {
        List<ItineraryFindResponse> itineraryFindResponseList = new ArrayList<>();

        for (Itinerary itinerary : trip.getItineraryList()) {
            ItineraryFindResponse itineraryResponse;

            if (itinerary instanceof Transport) {
                itineraryResponse = ItineraryFindResponse.fromEntity((Transport) itinerary);
            } else if (itinerary instanceof Accommodation) {
                itineraryResponse = ItineraryFindResponse.fromEntity((Accommodation) itinerary);
            } else {
                itineraryResponse = ItineraryFindResponse.fromEntity((Stay) itinerary);
            }

            itineraryFindResponseList.add(itineraryResponse);
        }

        return TripFindResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .isDomestic(trip.isDomestic())
                .itineraryList(itineraryFindResponseList)
                .build();
    }

}
