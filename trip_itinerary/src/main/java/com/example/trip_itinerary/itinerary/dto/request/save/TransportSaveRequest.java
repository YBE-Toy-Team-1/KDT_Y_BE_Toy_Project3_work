package com.example.trip_itinerary.itinerary.dto.request.save;

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
public class TransportSaveRequest extends ItinerarySaveRequest {

    @NotBlank(message = "이동 방법을 입력해주세요.")
    @Size(max = 30, message = "이동 방법은 최대 30자입니다.")
    private String transportation;

    @NotBlank(message = "출발 장소를 입력해주세요.")
    @Size(max = 30, message = "출발 장소는 최대 30자입니다.")
    private String departureLocation;

    @NotBlank(message = "출발 장소 주소를 입력해주세요.")
    @Size(max = 50, message = "출발 장소 주소는 최대 50자입니다.")
    private String departureAddress;

    @NotBlank(message = "도착 장소를 입력해주세요.")
    @Size(max = 30, message = "도착 장소는 최대 30자입니다.")
    private String arrivalLocation;

    @NotBlank(message = "도착 장소 주소를 입력해주세요.")
    @Size(max = 50, message = "도착 장소 주소는 최대 50자입니다.")
    private String arrivalAddress;

    @NotNull(message = "출발 시간을 입력해주세요.")
    private String departureDateTime;

    @NotNull(message = "도착 시간을 입력해주세요.")
    private String arrivalDateTime;

    @Override
    public String getStartDateTime() {
        return departureDateTime;
    }

    @Override
    public String getEndDateTime() {
        return arrivalDateTime;
    }
}
