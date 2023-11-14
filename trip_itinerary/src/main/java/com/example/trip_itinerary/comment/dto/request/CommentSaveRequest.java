package com.example.trip_itinerary.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentSaveRequest {

    private String content;

    public CommentSaveRequest(String content) {
        this.content = content;
    }
}
