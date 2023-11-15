package com.example.trip_itinerary.member.exception;

import com.example.trip_itinerary.member.controller.MemberController;
import com.example.trip_itinerary.member.dto.response.MemberErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<MemberErrorResponse> handle(MemberNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(MemberErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<MemberErrorResponse> handle(EmailAlreadyExistsException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(MemberErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<MemberErrorResponse> handle(LoginFailedException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(MemberErrorResponse.from(e.getErrorCode()));
    }

}
