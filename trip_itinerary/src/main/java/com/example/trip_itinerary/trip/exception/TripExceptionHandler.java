package com.example.trip_itinerary.trip.exception;

import com.example.trip_itinerary.trip.controller.TripController;
import com.example.trip_itinerary.trip.dto.response.TripErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = TripController.class)
public class TripExceptionHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public TripErrorResponse handle(TripNotFoundException e) {
        return TripErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public TripErrorResponse handle(InvalidDateRangeException e) {
        return TripErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public TripErrorResponse handle(InvalidDateFormatException e) {
        return TripErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
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
    public TripErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return TripErrorResponse.from(TripErrorCode.NOT_MATCH_DATA_TYPE);
    }

}