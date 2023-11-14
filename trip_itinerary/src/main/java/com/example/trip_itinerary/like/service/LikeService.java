package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.like.repository.LikeRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import com.example.trip_itinerary.member.exception.MemberNotFoundException;
import com.example.trip_itinerary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final TripRepository tripRepository;

    public void likeTrip(Long tripId, Long userId) {
        Member findMember = memberRepository.findById(userId).orElseThrow(
                () -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        likeRepository.findByUserAndTrip(findMember, trip).ifPresentOrElse(
                likeRepository::delete,
                () -> {
                    Likes like = new Likes(findMember, trip);
                    likeRepository.save(like);
                }
        );
    }

}
