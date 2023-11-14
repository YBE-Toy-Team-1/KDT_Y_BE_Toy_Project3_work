package com.example.trip_itinerary.itinerary.exception;

import com.example.trip_itinerary.itinerary.controller.ItineraryController;
import com.example.trip_itinerary.itinerary.dto.response.ItineraryErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackages = "com.example.trip_itinerary")
public class ItineraryExceptionHandler {

    @ExceptionHandler(ItineraryNotFoundException.class)
    public ResponseEntity<ItineraryErrorResponse> handle(ItineraryNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ItineraryErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(InvalidDateTimeRangeException.class)
    public ResponseEntity<ItineraryErrorResponse> handle(InvalidDateTimeRangeException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ItineraryErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ResponseEntity<ItineraryErrorResponse> handle(InvalidDateTimeFormatException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ItineraryErrorResponse.from(e.getErrorCode()));
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
    public ResponseEntity<ItineraryErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        int status = ItineraryErrorCode.NOT_MATCH_DATA_TYPE.getStatus();
        ItineraryErrorResponse body = ItineraryErrorResponse.from(ItineraryErrorCode.NOT_MATCH_DATA_TYPE);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(KakaoApiException.class)
    public ResponseEntity<ItineraryErrorResponse> handle(KakaoApiException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ItineraryErrorResponse.from(ItineraryErrorCode.API_REQUEST_FAILED));
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ItineraryErrorResponse> handle(ServerErrorException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ItineraryErrorResponse.from(ItineraryErrorCode.SERVER_ERROR));
    }

}