package com.example.trip_itinerary.like.repository;

import com.example.trip_itinerary.like.domain.Like;
import com.example.trip_itinerary.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByTrip(Trip trip);
}
