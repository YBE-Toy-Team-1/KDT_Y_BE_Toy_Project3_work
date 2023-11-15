package com.example.trip_itinerary.trip.dto.response;

import com.example.trip_itinerary.comment.dto.response.CommentFindResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripListFindResponse {
    private Long id;

    private String tripName;

    private LocalDate startDate;

    private LocalDate endDate;
    
    private boolean isDomestic;

    private Long likeNum;

    private List<CommentFindResponse> commentList;

    private String memberName;
}
