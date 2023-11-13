package com.example.trip_itinerary.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentNotFoundException extends RuntimeException {

    private CommentErrorCode errorCode;

}
