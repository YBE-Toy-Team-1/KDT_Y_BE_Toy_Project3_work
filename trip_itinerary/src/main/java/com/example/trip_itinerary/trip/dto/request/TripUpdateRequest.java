package com.example.trip_itinerary.trip.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripUpdateRequest {

    @NotBlank(message = "여행의 이름을 입력해주세요.")
    @Size(max = 30, message = "여행 이름은 최대 30자입니다.")
    private String name;

    @NotNull(message = "여행의 시작 날짜를 입력해주세요.")
    private String startDate;

    @NotNull(message = "여행의 종료 날짜를 입력해주세요.")
    private String endDate;

    @NotNull(message = "여행의 국내/국외 타입을 입력해주세요.")
    @JsonProperty("is_domestic")
    private Boolean isDomestic;

}
