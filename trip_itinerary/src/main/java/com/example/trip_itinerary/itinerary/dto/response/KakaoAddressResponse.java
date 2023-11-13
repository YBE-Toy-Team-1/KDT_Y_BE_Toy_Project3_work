package com.example.trip_itinerary.itinerary.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoAddressResponse {
    private List<String> roadAddressNames;
}
