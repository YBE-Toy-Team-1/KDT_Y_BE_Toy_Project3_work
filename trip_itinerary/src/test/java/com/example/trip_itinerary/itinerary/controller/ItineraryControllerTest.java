package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.WithMember;
import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.service.ItineraryService;
import com.example.trip_itinerary.itinerary.service.KakaoApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@WebMvcTest(ItineraryController.class)
class ItineraryControllerTest {
    private MockMvc mvc;

    @MockBean
    private ItineraryService itineraryService;

    @MockBean
    private KakaoApiService kakaoApiService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("숙박 여정 추가 url의 요청 및 응답 성공")
    @WithMember(username = "tester", password = "1234asd!@")
    void itinerarySaveSuccess() throws Exception {
        AccommodationSaveRequest accommodationSaveRequest = AccommodationSaveRequest.builder()
                .name("첫째날 숙박 일정")
                .accommodationName("신라 호텔")
                .accommodationAddress("서울특별시 중구 동호로 249 (장충동2가)")
                .checkInTime("2023-12-10T19:00:00")
                .checkOutTime("2023-12-10T11:00:00")
                .build();

        mvc.perform(post("/trips/2/accommodation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(accommodationSaveRequest))
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/trips/2"))
                .andDo(print());
    }
}