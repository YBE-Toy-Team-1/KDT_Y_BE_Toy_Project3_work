package com.example.trip_itinerary.itinerary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItineraryErrorCode {

    INVALID_DATE_TIME_RANGE("여정 종료 시점과 시작 시점을 확인하세요.", 400),
    INVALID_DATE_TIME_FORMAT("날짜, 시간 형식이 맞지 않습니다.", 400),
    ITINERARY_NOT_FOUND("찾는 여정이 존재하지 않습니다.", 404),
    NOT_MATCH_DATA_TYPE("데이터 형식이 올바르지 않습니다.", 400);

    private final String message;
    private final int status;

}
