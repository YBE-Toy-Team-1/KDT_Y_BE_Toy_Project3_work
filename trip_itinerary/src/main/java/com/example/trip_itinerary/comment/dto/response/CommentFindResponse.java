package com.example.trip_itinerary.comment.dto.response;

import com.example.trip_itinerary.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentFindResponse {
    private Long id;
    private String memberName;
    private String content;

    @Builder
    public CommentFindResponse(Long id, String memberName, String content) {
        this.id = id;
        this.memberName = memberName;
        this.content = content;
    }

    public static CommentFindResponse fromEntity(Comment comment) {
        return CommentFindResponse.builder()
                .id(comment.getId())
                .memberName(comment.getMember().getName())
                .content(comment.getContent())
                .build();
    }
}
