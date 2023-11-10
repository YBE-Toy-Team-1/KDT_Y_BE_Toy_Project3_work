package com.example.trip_itinerary.itinerary.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("transport")
public class Transport extends Itinerary {

    @Column(nullable = false, length = 30)
    private String transportation;

    @Column(name = "departure_location", nullable = false, length = 30)
    private String departureLocation;

    @Column(name = "arrival_location", nullable = false, length = 30)
    private String arrivalLocation;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;

    protected Transport() {
    }

    private Transport(String name, Trip trip, String transportation, String departureLocation, String arrivalLocation,
                      LocalDateTime departureDate, LocalDateTime arrivalDateTime) {
        super(null, name, trip);
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDateTime = departureDate;
        this.arrivalDateTime = arrivalDateTime;
    }

    public static Transport of(String name, Trip trip, String transportation, String startLocation, String endLocation,
                               LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new Transport(name, trip, transportation, startLocation, endLocation, startDateTime, endDateTime);
    }

    public void updateTransport(String name, String transportation, String startLocation,
                                String endLocation, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        super.updateItinerary(name);

        if (transportation != null) {
            this.transportation = transportation;
        }

        if (startLocation != null) {
            this.departureLocation = startLocation;
        }

        if (endLocation != null) {
            this.arrivalLocation = endLocation;
        }

        if (startDateTime != null) {
            this.departureDateTime = startDateTime;
        }

        if (endDateTime != null) {
            this.arrivalDateTime = endDateTime;
        }
    }

    public String getTransportation() {
        return transportation;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

}
