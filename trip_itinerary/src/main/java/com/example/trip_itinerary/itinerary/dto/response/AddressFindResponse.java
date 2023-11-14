package com.example.trip_itinerary.itinerary.dto.response;

import lombok.*;

@AllArgsConstructor
@Builder
public class AddressFindResponse {
    private String placeName;
    private String address;
}
