package com.example.trip_itinerary.member.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginFailedException extends RuntimeException{
    private MemberErrorCode errorCode;
}
