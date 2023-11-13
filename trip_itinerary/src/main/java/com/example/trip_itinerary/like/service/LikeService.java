package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.like.repository.LikeRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.repository.MemberRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository userRepository;
    private final TripRepository tripRepository;

    public void likeTrip(Long tripId, Long userId) {
        Member foundMember = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Trip trip = tripRepository.findById(tripId).orElseThrow(RuntimeException::new);
        likeRepository.findByMemberAndTrip(foundMember, trip).ifPresentOrElse(
                like -> {
                    likeRepository.delete(like);
//                    trip.deleteLike();
                },
                () -> {
                    Likes like = new Likes(foundMember, trip);
                    likeRepository.save(like);
//                    trip.addLike();
                }
        );
    }

}
