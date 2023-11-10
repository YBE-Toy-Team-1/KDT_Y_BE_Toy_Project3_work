package com.example.trip_itinerary.trip.controller;

import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.service.TripServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TripController.class)
class TripControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    TripServiceImpl tripService;

    @Test
    void findAllTripList() throws Exception {
        List<TripListFindResponse> tripListFindResponses = new ArrayList<>();

        tripListFindResponses.add(TripListFindResponse.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .isDomestic(false)
                .itineraryNameList(List.of("name1", "name2"))
                .build());

        tripListFindResponses.add(TripListFindResponse.builder()
                .id(2L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .isDomestic(true)
                .itineraryNameList(List.of("name5", "name6"))
                .build());

        given(tripService.findAllTrips())
                .willReturn(tripListFindResponses);

        mvc.perform(
                        get("/trips")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$..id", 1).exists());

    }

    @Test
    void findOneTrip()throws Exception{
        Long findId = 1L;
        TripFindResponse tripFindResponse = new TripFindResponse(1L, "testName", LocalDate.now(), LocalDate.now(), false, null);
        given(tripService.getTripById(any()))
                .willReturn(tripFindResponse);

        mvc.perform(
                        get("/trips/1")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", findId).exists());
    }

    @Test
    void saveTrip() throws Exception{
        Long id = 1L;
        given(tripService.saveTrip(any()))
                .willReturn(id);

        mvc.perform(
                        post("/trips")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", id).exists());
    }

    @Test
    void patchTripById() throws Exception{
        Long findId = 1L;
        given(tripService.updateTrip(any(), any()))
                .willReturn(findId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String patchRequestJson = objectMapper.writeValueAsString(
                TripPatchRequest.builder()
                .name("ansdas")
                .startDate(LocalDate.now().toString())
                .endDate(LocalDate.now().toString())
                .isDomestic(false)
                .build()
        );

        mvc.perform(
                    patch("/trips/1")
                        .content(patchRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", findId).exists());
    }
}