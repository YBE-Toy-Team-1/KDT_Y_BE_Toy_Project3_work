package com.example.trip_itinerary.like.repository;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndTrip(Member member, Trip trip);

}
