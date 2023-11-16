package com.example.trip_itinerary.itinerary.dto.response;


import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItineraryFindResponse {

    private final String name;

    private final String transportation;

    private final String departureLocation;

    private final String departureAddress;

    private final String arrivalLocation;

    private final String arrivalAddress;

    private final LocalDateTime departureDateTime;

    private final LocalDateTime arrivalDateTime;

    private final LocalDateTime leaveDateTime;

    private final String accommodationName;

    private final String accommodationAddress;

    private final LocalDateTime checkInTime;

    private final LocalDateTime checkOutTime;

    private final String location;

    private final String locationAddress;

    @Builder
    public ItineraryFindResponse(
            String name, String transportation, String departureLocation, String departureAddress, String arrivalLocation,
            String arrivalAddress, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
            LocalDateTime leaveDateTime, String accommodationName, String accommodationAddress,
            LocalDateTime checkInTime, LocalDateTime checkOutTime, String location, String locationAddress
    ) {
        this.name = name;
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.departureAddress = departureAddress;
        this.arrivalLocation = arrivalLocation;
        this.arrivalAddress = arrivalAddress;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.leaveDateTime = leaveDateTime;
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.location = location;
        this.locationAddress = locationAddress;
    }

    public static ItineraryFindResponse fromEntity(Transport transport) {
        return ItineraryFindResponse.builder()
                .name(transport.getName())
                .transportation(transport.getTransportation())
                .departureLocation(transport.getDepartureLocation())
                .departureAddress(transport.getDepartureAddress())
                .arrivalLocation(transport.getArrivalLocation())
                .arrivalAddress(transport.getArrivalAddress())
                .departureDateTime(transport.getStartDateTime())
                .arrivalDateTime(transport.getEndDateTime())
                .build();
    }

    public static ItineraryFindResponse fromEntity(Accommodation accommodation) {
        return ItineraryFindResponse.builder()
                .name(accommodation.getName())
                .accommodationName(accommodation.getName())
                .accommodationAddress(accommodation.getAccommodationAddress())
                .checkInTime(accommodation.getStartDateTime())
                .checkOutTime(accommodation.getEndDateTime())
                .build();
    }

    public static ItineraryFindResponse fromEntity(Stay stay) {
        return ItineraryFindResponse.builder()
                .name(stay.getName())
                .location(stay.getLocation())
                .locationAddress(stay.getLocationAddress())
                .arrivalDateTime(stay.getStartDateTime())
                .leaveDateTime(stay.getEndDateTime())
                .build();
    }

}
