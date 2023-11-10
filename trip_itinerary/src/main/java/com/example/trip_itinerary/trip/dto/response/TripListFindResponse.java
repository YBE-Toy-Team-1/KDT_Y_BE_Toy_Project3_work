package com.example.trip_itinerary.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripListFindResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonProperty("is_domestic")
    private boolean isDomestic;
    private List<String> itineraryNameList;

}
