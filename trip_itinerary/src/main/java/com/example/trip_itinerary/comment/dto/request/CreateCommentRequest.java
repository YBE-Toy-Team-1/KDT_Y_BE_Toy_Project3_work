package com.example.trip_itinerary.comment.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private Long tripId;
    private Long commentId;
    private String content;

    public CreateCommentRequest(Long tripId, Long commentId, String content) {
        this.tripId = tripId;
        this.commentId = commentId;
        this.content = content;
    }
}
