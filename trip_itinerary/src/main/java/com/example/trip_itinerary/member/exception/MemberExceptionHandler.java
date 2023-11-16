package com.example.trip_itinerary.member.exception;

import com.example.trip_itinerary.TripItineraryApplication;
import com.example.trip_itinerary.member.dto.response.MemberErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = TripItineraryApplication.class)
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMessageBuilder = new StringBuilder();

        for (FieldError fieldError : fieldErrors) {
            errorMessageBuilder.append(fieldError.getDefaultMessage()).append("\n");
        }

        String errorMessage = errorMessageBuilder.toString().trim(); // Remove trailing newline

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MemberErrorResponse> handle(HttpMessageNotReadableException e) {
        int status = MemberErrorCode.INVALID_USERNAME_PASSWORD.getStatus();
        MemberErrorResponse body = MemberErrorResponse.from(MemberErrorCode.INVALID_USERNAME_PASSWORD);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MemberNotMatchedException.class)
    public ResponseEntity<MemberErrorResponse> handle(MemberNotMatchedException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(MemberErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(LoginCredentialExpired.class)
    public ResponseEntity<MemberErrorResponse> handle(LoginCredentialExpired e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(MemberErrorResponse.from(e.getErrorCode()));
    }

}
