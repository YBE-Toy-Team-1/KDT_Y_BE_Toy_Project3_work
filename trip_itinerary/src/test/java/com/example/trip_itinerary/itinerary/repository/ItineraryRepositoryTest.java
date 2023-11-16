package com.example.trip_itinerary.itinerary.repository;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class ItineraryRepositoryTest {
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private TripRepository tripRepository;

    @Test
    @DisplayName("숙박 여정 저장 성공")
    void accommodationSaveSuccess(){
        Member member = Member.of("김패캠", "gmnkmc@naver.com", "123@#!fdf");

        Trip trip = Trip.of("서울 여행", LocalDate.of(2023, 12, 10),
                LocalDate.of(2023, 12, 11), true, member);

        Itinerary accommodation = Accommodation.of("첫째날 숙박 일정", trip, "신라 호텔",
                "서울특별시 중구 동호로 249 (장충동2가)", LocalDateTime.of(2023, 12, 10, 19, 00),
                LocalDateTime.of(2023, 12, 11, 11, 00));

        tripRepository.save(trip);
        Itinerary savedAccommodation = itineraryRepository.save(accommodation);

        Assertions.assertThat(savedAccommodation).isEqualTo(accommodation);
    }
}