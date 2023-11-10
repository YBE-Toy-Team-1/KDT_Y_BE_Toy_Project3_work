package com.example.trip_itinerary.itinerary.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", length = 30)
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    protected Itinerary() {
    }

    protected Itinerary(Long id, String name, Trip trip) {
        this.id = id;
        this.name = name;
        this.trip = trip;
    }

    public void updateItinerary(String name) {
        if (name != null) {
            this.name = name;
        }
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

}

