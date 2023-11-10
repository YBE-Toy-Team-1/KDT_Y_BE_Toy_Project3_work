package com.example.trip_itinerary.itinerary.dto.request.save;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StaySaveRequest extends ItinerarySaveRequest {

    @NotBlank(message = "체류 장소를 입력해주세요.")
    @Size(max = 30, message = "체류 장소는 최대 30자입니다.")
    private String location;

    @NotNull(message = "체류 장소 도착 시간을 입력해주세요.")
    private String arrivalDateTime;

    @NotNull(message = "체류 장소에서 떠날 시간을 입력해주세요.")
    private String leaveDateTime;

}
