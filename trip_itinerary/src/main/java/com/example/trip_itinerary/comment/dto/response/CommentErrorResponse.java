package com.example.trip_itinerary.comment.dto.response;

import com.example.trip_itinerary.comment.exception.CommentErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentErrorResponse {

    private String errorCode;
    private String message;
    private int status;

    public static CommentErrorResponse from(CommentErrorCode commentErrorCode) {
        return CommentErrorResponse.builder()
                .errorCode(commentErrorCode.name())
                .message(commentErrorCode.getMessage())
                .status(commentErrorCode.getStatus())
                .build();
    }

}
