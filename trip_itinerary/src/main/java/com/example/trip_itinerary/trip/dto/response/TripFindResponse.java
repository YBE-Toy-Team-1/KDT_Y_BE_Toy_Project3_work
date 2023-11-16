package com.example.trip_itinerary.trip.dto.response;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.comment.dto.response.CommentFindResponse;
import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.itinerary.dto.response.ItineraryFindResponse;
import com.example.trip_itinerary.trip.domain.Trip;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripFindResponse {

    private Long id;

    private String tripName;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isDomestic;

    private Long likeNum;

    private String memberName;

    private List<CommentFindResponse> commentResponseList;

    private List<ItineraryFindResponse> itineraryList;

    @Builder
    public TripFindResponse(
            Long id, String tripName, LocalDate startDate, LocalDate endDate, boolean isDomestic,
            Long likeNum, String memberName, List<CommentFindResponse> commentResponseList, List<ItineraryFindResponse> itineraryList
    ) {
        this.id = id;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDomestic = isDomestic;
        this.likeNum = likeNum;
        this.memberName = memberName;
        this.commentResponseList = commentResponseList;
        this.itineraryList = itineraryList;
    }

    public static TripFindResponse fromEntity(Trip trip) {
        List<CommentFindResponse> findCommentResponsesList = new ArrayList<>();

        for (Comment comment : trip.getCommentList()) {
            findCommentResponsesList.add(CommentFindResponse.fromEntity(comment));
        }

        List<ItineraryFindResponse> itineraryFindResponseList = new ArrayList<>();

        for (Itinerary itinerary : trip.getItineraryList()) {
            ItineraryFindResponse itineraryResponse;

            if (itinerary instanceof Transport) {
                itineraryResponse = ItineraryFindResponse.fromEntity((Transport) itinerary);
            } else if (itinerary instanceof Accommodation) {
                itineraryResponse = ItineraryFindResponse.fromEntity((Accommodation) itinerary);
            } else {
                itineraryResponse = ItineraryFindResponse.fromEntity((Stay) itinerary);
            }

            itineraryFindResponseList.add(itineraryResponse);
        }

        return TripFindResponse.builder()
                .id(trip.getId())
                .tripName(trip.getName())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .isDomestic(trip.isDomestic())
                .likeNum(trip.getLikeNum())
                .memberName(trip.getMember().getName())
                .commentResponseList(findCommentResponsesList)
                .itineraryList(itineraryFindResponseList)
                .build();
    }

}
