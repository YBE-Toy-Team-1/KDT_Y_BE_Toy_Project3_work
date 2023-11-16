package com.example.trip_itinerary.itinerary.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", length = 30)
public abstract class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    protected Itinerary() {
    }

    protected Itinerary(String name, Trip trip) {
        this.name = name;
        this.trip = trip;
    }

    public void updateItinerary(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Trip getTrip() {
        return trip;
    }

    public abstract LocalDateTime getStartDateTime();
    public abstract LocalDateTime getEndDateTime();
}

