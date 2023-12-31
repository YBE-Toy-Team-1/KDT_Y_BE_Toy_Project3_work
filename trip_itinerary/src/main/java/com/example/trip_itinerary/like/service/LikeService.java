package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.like.repository.LikeRepository;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;

    public void likeTrip(MemberAdapter memberAdapter, Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        likeRepository.findByTripAndMember(trip, memberAdapter.getMember()).ifPresentOrElse(
                (like) -> {
                    likeRepository.delete(like);
                    trip.downLike();
                },
                () -> {
                    Likes like = Likes.of(memberAdapter.getMember(), trip);
                    trip.upLike();
                    likeRepository.save(like);
                }
        );
    }

}
