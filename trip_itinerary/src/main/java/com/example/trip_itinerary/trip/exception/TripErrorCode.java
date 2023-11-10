package com.example.trip_itinerary.trip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripErrorCode {

    INVALID_DATE_RANGE("여행 종료 날짜와 여행 시작 날짜를 확인해주세요.", 400),
    INVALID_DATE_FORMAT("날짜 형식이 맞지 않습니다.", 400),
    TRIP_NOT_FOUND("찾는 여행이 존재하지 않습니다.", 404),
    NOT_MATCH_DATA_TYPE("데이터 형식이 올바르지 않습니다.", 400);


    private final String message;
    private final int status;

}
