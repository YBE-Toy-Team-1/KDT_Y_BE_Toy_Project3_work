package com.example.trip_itinerary.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentNotFoundException extends RuntimeException {

    private CommentErrorCode errorCode;

}
