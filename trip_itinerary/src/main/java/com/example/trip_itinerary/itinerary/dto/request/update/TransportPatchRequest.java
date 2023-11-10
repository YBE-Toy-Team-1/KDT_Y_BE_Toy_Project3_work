package com.example.trip_itinerary.itinerary.dto.request.update;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransportPatchRequest extends ItineraryPatchRequest {

    @Size(max = 30, message = "이동 방법은 최대 30자입니다.")
    private String transportation;
    @Size(max = 30, message = "출발 장소는 최대 30자입니다.")
    private String departureLocation;
    @Size(max = 30, message = "도착 장소는 최대 30자입니다.")
    private String arrivalLocation;
    private String departureDateTime;
    private String arrivalDateTime;

}
