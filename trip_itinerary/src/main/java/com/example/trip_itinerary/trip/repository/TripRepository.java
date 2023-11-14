package com.example.trip_itinerary.trip.repository;

import com.example.trip_itinerary.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByNameContains(String tripName);
}
