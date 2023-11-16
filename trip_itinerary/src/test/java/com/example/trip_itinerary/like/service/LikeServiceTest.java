package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.like.repository.LikeRepository;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    @DisplayName("좋아요 추가 성공 테스트")
    public void likeSaveSuccess(){
        Member tester = Member.of("tester", "qseqfg@naver.com", "1234@agsr");
        Trip trip = Trip.of("일본 여행", LocalDate.of(2022, 10, 2),
                LocalDate.of(2022, 10, 12), false, tester);
        Likes like = Likes.of(tester, trip);

        given(tripRepository.findById(any())).willReturn(Optional.of(trip));
        given(likeRepository.save(any())).willReturn(like);

        likeService.likeTrip(new MemberAdapter(tester), 1L);
        likeService.likeTrip(new MemberAdapter(tester), 1L);

        Assertions.assertThat(trip.getLikeNum()).isEqualTo(2L);
    }
}