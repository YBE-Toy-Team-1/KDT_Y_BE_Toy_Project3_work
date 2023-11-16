package com.example.trip_itinerary.trip.repository;

import com.example.trip_itinerary.WithMember;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.repository.MemberRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("여행 저장")
    @WithMember(username = "tester", password = "1234asd!@")
    void saveTripSuccess() {
        //given
        Member member = Member.of("tester", "tester@anfsa.com", "1234@@eeaqs");
        Trip trip = Trip.of("한국 여행",LocalDate.of(2023,11,16),LocalDate.of(2023,11,17),true,member);

        //when
        memberRepository.save(member);
        Trip savedTrip = tripRepository.save(trip);

        //then
        assertEquals(savedTrip.getName(),"한국 여행");
    }

    @Test
    @DisplayName("여행 이름으로 조회")
    void searchTripByNameSuccess() {
        //given
        Member member = Member.of("tester", "tester@anfsa.com", "1234@@eeaqs");

        Trip trip = Trip.of("한국 여행",LocalDate.of(2023,11,16),LocalDate.of(2023,11,17),true,member);
        Trip trip2 = Trip.of("일본 여행",LocalDate.of(2023,11,16),LocalDate.of(2023,11,17),true,member);

        memberRepository.save(member);
        tripRepository.save(trip);
        tripRepository.save(trip2);

        String tripName = "한국";

        //when
        List<Trip> tripList = tripRepository.findByNameContains(tripName);

        //then
        assertEquals(tripList.get(0).getName(),"한국 여행");
    }

}