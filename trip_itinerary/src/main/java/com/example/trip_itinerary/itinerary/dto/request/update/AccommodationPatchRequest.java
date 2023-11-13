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
public class AccommodationPatchRequest extends ItineraryPatchRequest {

    @Size(max = 30, message = "숙소 이름은 최대 30자입니다.")
    private String accommodationName;

    @Size(max = 30, message = "체류 장소는 최대 30자입니다.")
    private String roadAddress;

    private String checkInTime;
    private String checkOutTime;

}
