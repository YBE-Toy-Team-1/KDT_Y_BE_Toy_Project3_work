package com.example.trip_itinerary.comment.dto.data;

import lombok.Getter;

@Getter
public class CommentData {
    private Long commentId;
    private String content;
    private Long userId;
    private String userName;

    public CommentData(Long commentId, String content, Long userId, String userName) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }
}
