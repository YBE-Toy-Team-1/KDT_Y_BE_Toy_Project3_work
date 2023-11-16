package com.example.trip_itinerary.trip.controller;

import com.example.trip_itinerary.WithMember;
import com.example.trip_itinerary.itinerary.dto.response.ItineraryFindResponse;
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
    MockMvc mvc;
    @MockBean
    TripService tripService;

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
        List<ItineraryFindResponse> itineraryList;

        given(tripService.findAllTrips())
                .willReturn(tripListFindResponses);

        mvc.perform(
                    get("/trips")
                )
                .andExpect(status().isOk())
                .andDo(print());

    }
//    @Test
//    void findOneTrip()throws Exception{
//        Long findId = 1L;
//        TripFindResponse tripFindResponse = new TripFindResponse(1L, "testName", LocalDate.now(), LocalDate.now(), false, null);
//        given(tripService.getTripById(any()))
//                .willReturn(tripFindResponse);
//
//        mvc.perform(
//                        get("/trips/1")
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.id", findId).exists());
//    }
//
//    @Test
//    void saveTrip() throws Exception{
//        Long id = 1L;
//        given(tripService.saveTrip(any()))
//                .willReturn(id);
//
//        mvc.perform(
//                        post("/trips")
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.id", id).exists());
//    }
//
//    @Test
//    void patchTripById() throws Exception{
//        Long findId = 1L;
//        given(tripService.updateTrip(any(), any()))
//                .willReturn(findId);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String patchRequestJson = objectMapper.writeValueAsString(
//                TripPatchRequest.builder()
//                .name("ansdas")
//                .startDate(LocalDate.now().toString())
//                .endDate(LocalDate.now().toString())
//                .isDomestic(false)
//                .build()
//        );
//
//        mvc.perform(
//                    patch("/trips/1")
//                        .content(patchRequestJson)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$", findId).exists());
//    }
}