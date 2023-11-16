package com.example.trip_itinerary.like.repository;

import com.example.trip_itinerary.like.domain.Likes;
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



@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    LikeRepository likeRepository;

    @Test
    @DisplayName("좋아요 저장 성공 테스트")
    void likeSave(){
        Member tester = Member.of("tester", "tester@anfsa.com", "1234@@eeaqs");
        Trip trip = Trip.of("제주도 여행", LocalDate.of(2023, 6, 2),
                LocalDate.of(2023, 6, 5), true, tester);
        Likes likes = Likes.of(tester, trip);

        Likes savedLike = likeRepository.save(likes);

        Assertions.assertThat(savedLike).isEqualTo(likes);
    }
}