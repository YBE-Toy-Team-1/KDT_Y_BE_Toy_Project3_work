package com.example.trip_itinerary.like.repository;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Long countByTrip(Trip findTrip);

    Optional<Likes> findByUserAndTrip(User user, Trip trip);

}
