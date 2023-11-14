package com.example.trip_itinerary.comment.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private Long tripId;
    private String content;

    public CreateCommentRequest(Long tripId, String content) {
        this.tripId = tripId;
        this.content = content;
    }
}
