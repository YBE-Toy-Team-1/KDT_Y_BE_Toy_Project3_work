package com.example.trip_itinerary.itinerary.dto.request.update;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class ItineraryPatchRequest {

    @Size(max = 30, message = "여정 이름은 최대 30자입니다.")
    private String name;

    public abstract String getStartDateTime();
    public abstract String getEndDateTime();

}
