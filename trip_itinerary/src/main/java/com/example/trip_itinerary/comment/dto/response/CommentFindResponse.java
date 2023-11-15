package com.example.trip_itinerary.comment.dto.response;


import com.example.trip_itinerary.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentFindResponse {
    private Long id;
    private String memberName;
    private String content;

    public static CommentFindResponse fromEntity(Comment comment){
        return CommentFindResponse.builder()
                .id(comment.getId())
                .memberName(comment.getMember().getName())
                .content(comment.getContent())
                .build();
    }
}
