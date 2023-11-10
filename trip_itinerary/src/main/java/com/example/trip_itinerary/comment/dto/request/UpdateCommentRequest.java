package com.example.trip_itinerary.comment.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    private Long tripId;
    private String content;
}
