package com.example.trip_itinerary.member.exception;

import com.example.trip_itinerary.member.controller.MemberController;
import com.example.trip_itinerary.member.dto.response.MemberErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public MemberErrorResponse handle(MemberNotFoundException e) {
        return MemberErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public MemberErrorResponse handle(EmailAlreadyExistsException e) {
        return MemberErrorResponse.from(e.getErrorCode());
    }

}
