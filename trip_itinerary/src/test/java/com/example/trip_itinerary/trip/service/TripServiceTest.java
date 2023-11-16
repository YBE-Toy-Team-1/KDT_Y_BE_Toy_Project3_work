package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {
    @Mock
    private TripRepository tripRepository;

    @Mock
    private Member mockMember;

    @InjectMocks
    private TripService tripService;

    @Test
    @DisplayName("여행 전체 조회 테스트 ")
    void findAllTrips_Success() {
        //given
        List<Trip> trips = Arrays.asList(
                Trip.of("testName", LocalDate.now(), LocalDate.now(), false, mockMember),
                Trip.of("testName2", LocalDate.now(), LocalDate.now(), false, mockMember)
        );
        when(tripRepository.findAll()).thenReturn(trips);

        //when
        List<TripListFindResponse> responses = tripService.findAllTrips();

        //then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(responses.get(0).getTripName(), "testName");
        verify(tripRepository).findAll();
    }
}