package com.example.trip_itinerary.itinerary.dto.response;

import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import lombok.Builder;

import javax.naming.ldap.StartTlsResponse;
import java.time.LocalDateTime;

@Builder
public class StayFindResponse extends ItineraryFindResponse{

    private String location;

    private LocalDateTime arrivalDateTime;

    private LocalDateTime leaveDateTime;

    public static ItineraryFindResponse fromEntity(Itinerary stayEntity){
        Stay stay = (Stay) stayEntity;
        return StayFindResponse.builder()
                .location(stay.getLocation())
                .arrivalDateTime(stay.getStartDateTime())
                .leaveDateTime(stay.getEndDateTime())
                .build();
    }
}
