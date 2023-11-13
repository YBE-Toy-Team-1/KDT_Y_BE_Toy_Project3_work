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

    @Column(name = "departure_address", nullable = false, length = 30)
    private String departureAddress;

    @Column(name = "arrival_location", nullable = false, length = 30)
    private String arrivalLocation;

    @Column(name = "arrival_address", nullable = false, length = 30)
    private String arrivalAddress;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;

    protected Transport() {
    }

    private Transport(String name, Trip trip, String transportation, String departureLocation,
                      String departureAddress, String arrivalLocation,String arrivalAddress,
                      LocalDateTime departureDate, LocalDateTime arrivalDateTime) {
        super(name, trip);
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.departureAddress = departureAddress;
        this.arrivalLocation = arrivalLocation;
        this.arrivalAddress = arrivalAddress;
        this.departureDateTime = departureDate;
        this.arrivalDateTime = arrivalDateTime;
    }

    public static Transport of(String name, Trip trip, String transportation, String departureLocation,
                               String departureAddress, String arrivalLocation, String arrivalAddress,
                               LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new Transport(name, trip, transportation, departureLocation, departureAddress, arrivalLocation,arrivalAddress, startDateTime, endDateTime);
    }

    public void updateTransport(String name, String transportation, String departureLocation, String departureAddress,
                                String arrivalLocation, String arrivalAddress,
                                LocalDateTime startDateTime, LocalDateTime endDateTime) {

        super.updateItinerary(name);
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.departureAddress = departureAddress;
        this.arrivalLocation = arrivalLocation;
        this.arrivalAddress = arrivalAddress;
        this.departureDateTime = startDateTime;
        this.arrivalDateTime = endDateTime;
    }

    public String getTransportation() {
        return transportation;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
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
