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
public class StayPatchRequest extends ItineraryPatchRequest {

    @Size(max = 30, message = "체류 장소는 최대 30자입니다.")
    private String location;

    @Size(max = 50, message = "체류 장소 주소는 최대 50자입니다.")
    private String locationAddress;

    private String arrivalDateTime;
    private String leaveDateTime;

    @Override
    public String getStartDateTime() {
        return arrivalDateTime;
    }

    @Override
    public String getEndDateTime() {
        return leaveDateTime;
    }

}
