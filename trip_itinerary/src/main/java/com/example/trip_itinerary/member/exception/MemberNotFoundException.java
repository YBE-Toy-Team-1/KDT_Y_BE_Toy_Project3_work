package com.example.trip_itinerary.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberNotFoundException extends RuntimeException {

    private MemberErrorCode errorCode;

}
