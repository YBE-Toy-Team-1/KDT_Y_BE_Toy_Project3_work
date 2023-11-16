package com.example.trip_itinerary.itinerary.dto.request.save;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public abstract class ItinerarySaveRequest {

    @NotBlank(message = "여정 이름을 입력해주세요.")
    @Size(max = 30, message = "여정 이름은 최대 30자입니다.")
    private String name;

    public ItinerarySaveRequest(String name) {
        this.name = name;
    }

    public abstract String getStartDateTime();

    public abstract String getEndDateTime();

}
