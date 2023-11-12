package com.example.trip_itinerary.comment.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class FindCommentResponse {
    private Long id;
    private String memberName;
    private String content;

    public static FindCommentResponse fromEntity(Comment comment){
        return FindCommentResponse.builder()
                .id(comment.getId())
                .memberName(comment.getMemberName())
                .content(comment.getContent())
                .build();
    }
}
