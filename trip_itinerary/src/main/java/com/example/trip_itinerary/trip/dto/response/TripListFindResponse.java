package com.example.trip_itinerary.trip.dto.response;

import com.example.trip_itinerary.comment.dto.response.CommentFindResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
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

    @Builder
    public TripListFindResponse(
            Long id, String tripName, LocalDate startDate, LocalDate endDate, boolean isDomestic,
            Long likeNum, List<CommentFindResponse> commentList, String memberName
    ) {
        this.id = id;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDomestic = isDomestic;
        this.likeNum = likeNum;
        this.commentList = commentList;
        this.memberName = memberName;
    }
}
