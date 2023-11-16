package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.repository.ItineraryRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItineraryServiceTest {
    @Mock
    private ItineraryRepository itineraryRepository;
    @Mock
    private TripRepository tripRepository;

    @Mock
    private ItineraryDateTimeValidationService itineraryDateTimeValidationService;

    @InjectMocks
    private ItineraryService itineraryService;

    @Test
    @DisplayName("숙박 여정 생성 성공")
    void saveItinerarySuccess() {
        Member member = Member.of("김패캠", "gmnkmc@naver.com", "123@#!fdf");

        Trip trip = Trip.of("서울 여행", LocalDate.of(2023, 12, 10),
                LocalDate.of(2023, 12, 11), true, member);

        Accommodation accommodation = Accommodation.of("첫째날 숙박 일정", trip, "신라 호텔",
"서울특별시 중구 동호로 249 (장충동2가)", LocalDateTime.of(2023, 12, 10, 19, 00),
                LocalDateTime.of(2023, 12, 11, 11, 00));

        AccommodationSaveRequest accommodationSaveRequest = AccommodationSaveRequest.builder()
                .name("첫째날 숙박 일정")
                .accommodationName("신라 호텔")
                .accommodationAddress("서울특별시 중구 동호로 249 (장충동2가)")
                .checkInTime("2023-12-10T19:00:00")
                .checkOutTime("2023-12-10T11:00:00")
                .build();

        given(itineraryRepository.save(any())).willReturn(accommodation);
        given(tripRepository.findById(any())).willReturn(Optional.of(trip));

        itineraryService.saveAccommodation(1L, accommodationSaveRequest, new MemberAdapter(member));

        Assertions.assertThat(accommodation.getAccommodationName()).isEqualTo(accommodationSaveRequest.getAccommodationName());
    }

}