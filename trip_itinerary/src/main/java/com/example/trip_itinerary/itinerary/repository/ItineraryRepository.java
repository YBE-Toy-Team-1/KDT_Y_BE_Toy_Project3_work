package com.example.trip_itinerary.itinerary.repository;

import com.example.trip_itinerary.itinerary.domain.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
}
