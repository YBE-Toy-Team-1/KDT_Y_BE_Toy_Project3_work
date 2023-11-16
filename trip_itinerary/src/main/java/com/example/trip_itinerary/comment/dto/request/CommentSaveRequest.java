package com.example.trip_itinerary.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

    private String content;

    public CommentSaveRequest(String content) {
        this.content = content;
    }
}
