package com.example.trip_itinerary.itinerary.dto.response;


import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItineraryFindResponse {

    private String name;
//
//    private String transportation;
//
//    private String departureLocation;
//
//    private String arrivalLocation;
//
//    private LocalDateTime departureDateTime;
//
//    private LocalDateTime arrivalDateTime;
//
//    private LocalDateTime leaveDateTime;
//
//    private String accommodationName;
//
//    private LocalDateTime checkInTime;
//
//    private LocalDateTime checkOutTime;
//
//    private String location;
//
//    public static ItineraryFindResponse fromEntity(Transport transport) {
//        return ItineraryFindResponse.builder()
//                .name(transport.getName())
//                .transportation(transport.getTransportation())
//                .departureLocation(transport.getDepartureLocation())
//                .arrivalLocation(transport.getArrivalLocation())
//                .departureDateTime(transport.getStartDateTime())
//                .arrivalDateTime(transport.getEndDateTime())
//                .build();
//    }
//
//    public static ItineraryFindResponse fromEntity(Accommodation accommodation) {
//        return ItineraryFindResponse.builder()
//                .name(accommodation.getName())
//                .accommodationName(accommodation.getName())
//                .checkInTime(accommodation.getStartDateTime())
//                .checkOutTime(accommodation.getEndDateTime())
//                .build();
//    }
//
//    public static ItineraryFindResponse fromEntity(Stay stay) {
//        return ItineraryFindResponse.builder()
//                .name(stay.getName())
//                .location(stay.getLocation())
//                .arrivalDateTime(stay.getStartDateTime())
//                .leaveDateTime(stay.getEndDateTime())
//                .build();
//    }

    private StayFindResponse stayFindResponse;

    public static ItineraryFindResponse fromEntity(Itinerary itinerary){
        return ItineraryFindResponse.builder()
                .name(itinerary.getName())
                .build();
    }

}
