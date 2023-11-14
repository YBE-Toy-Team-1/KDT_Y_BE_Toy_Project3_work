package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Like;
import com.example.trip_itinerary.like.repository.LikeRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;

    public void likeTrip(Member member, Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        likeRepository.findByTrip(trip).ifPresentOrElse(
                (like) -> {
                    likeRepository.delete(like);
                    trip.downLike();
                },
                () -> {
                    Like like = Like.of(member, trip);
                    trip.upLike();
                    likeRepository.save(like);
                }
        );
    }

}
