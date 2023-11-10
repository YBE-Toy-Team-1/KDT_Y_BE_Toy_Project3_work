package com.example.trip_itinerary.itinerary.dto.request.save;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StaySaveRequest.class, name = "stay"),
        @JsonSubTypes.Type(value = TransportSaveRequest.class, name = "transport"),
        @JsonSubTypes.Type(value = AccommodationSaveRequest.class, name = "accommodation")
})
public class ItinerarySaveRequest {

    @NotBlank(message = "여정 이름을 입력해주세요.")
    @Size(max = 30, message = "여정 이름은 최대 30자입니다.")
    private String name;

}
