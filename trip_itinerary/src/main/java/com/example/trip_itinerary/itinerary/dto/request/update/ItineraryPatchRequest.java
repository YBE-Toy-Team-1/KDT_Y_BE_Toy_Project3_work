package com.example.trip_itinerary.itinerary.dto.request.update;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StayPatchRequest.class, name = "stay"),
        @JsonSubTypes.Type(value = TransportPatchRequest.class, name = "transport"),
        @JsonSubTypes.Type(value = AccommodationPatchRequest.class, name = "accommodation")
})
public class ItineraryPatchRequest {

    @Size(max = 30, message = "여정 이름은 최대 30자입니다.")
    private String name;

}
