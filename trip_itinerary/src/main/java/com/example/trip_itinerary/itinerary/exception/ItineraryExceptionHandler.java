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

@RestControllerAdvice(basePackageClasses = ItineraryController.class)
public class ItineraryExceptionHandler {

    @ExceptionHandler(ItineraryNotFoundException.class)
    public ItineraryErrorResponse handle(ItineraryNotFoundException e) {
        return ItineraryErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(InvalidDateTimeRangeException.class)
    public ItineraryErrorResponse handle(InvalidDateTimeRangeException e) {
        return ItineraryErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ItineraryErrorResponse handle(InvalidDateTimeFormatException e) {
        return ItineraryErrorResponse.from(e.getErrorCode());
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
    public ItineraryErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ItineraryErrorResponse.from(ItineraryErrorCode.NOT_MATCH_DATA_TYPE);
    }

}