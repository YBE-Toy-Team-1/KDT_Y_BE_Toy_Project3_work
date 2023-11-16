package com.example.trip_itinerary.trip.controller;

import com.example.trip_itinerary.WithMember;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TripController.class)
class TripControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripService tripService;

    @Test
    @DisplayName("여행 전체 검색 성공")
    @WithMember(username = "tester", password = "1234qwe!@")
    void findAllTripList() throws Exception {
        List<TripListFindResponse> tripListFindResponses = new ArrayList<>();

        tripListFindResponses.add(TripListFindResponse.builder()
                .id(1L)
                .tripName("서울 여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .isDomestic(false)
                .build());

        tripListFindResponses.add(TripListFindResponse.builder()
                .id(2L)
                .tripName("부산 여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .isDomestic(true)
                .build());

        given(tripService.findAllTrips()).willReturn(tripListFindResponses);

        mvc.perform(
                    get("/trips")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}