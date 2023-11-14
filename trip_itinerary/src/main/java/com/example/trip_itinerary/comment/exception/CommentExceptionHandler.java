package com.example.trip_itinerary.comment.exception;

import com.example.trip_itinerary.comment.controller.CommentController;
import com.example.trip_itinerary.comment.dto.response.CommentErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = CommentController.class)
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public CommentErrorResponse handle(CommentNotFoundException e) {
        return CommentErrorResponse.from(e.getErrorCode());
    }

}
