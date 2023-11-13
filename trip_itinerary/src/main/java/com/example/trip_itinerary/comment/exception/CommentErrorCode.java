package com.example.trip_itinerary.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode {

    COMMENT_NOT_FOUND("해당 정보를 가진 댓글이 없습니다", 404);

    private final String message;
    private final int status;

}
