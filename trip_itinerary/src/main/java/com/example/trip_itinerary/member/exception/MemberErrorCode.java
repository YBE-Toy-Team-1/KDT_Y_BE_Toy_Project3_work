package com.example.trip_itinerary.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND("해당 정보를 가진 유저가 없습니다", 404),
    ALREADY_EMAIL_EXIST("이미 존재하는 이메일입니다.", 400);

    private final String message;
    private final int status;

}
