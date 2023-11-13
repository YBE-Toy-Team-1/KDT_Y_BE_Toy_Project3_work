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

    @Column(name = "departure_road_address", nullable = false, length = 30)
    private String departureRoadAddress;

    @Column(name = "arrival_location", nullable = false, length = 30)
    private String arrivalLocation;

    @Column(name = "arrival_road_address", nullable = false, length = 30)
    private String arrivalRoadAddress;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;

    protected Transport() {
    }

    private Transport(String name, Trip trip, String transportation, String departureLocation,
                      String departureRoadAddress, String arrivalLocation,String arrivalRoadAddress,
                      LocalDateTime departureDate, LocalDateTime arrivalDateTime) {
        super(name, trip);
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.departureRoadAddress = departureRoadAddress;
        this.arrivalLocation = arrivalLocation;
        this.arrivalRoadAddress = arrivalRoadAddress;
        this.departureDateTime = departureDate;
        this.arrivalDateTime = arrivalDateTime;
    }

    public static Transport of(String name, Trip trip, String transportation, String departureLocation, String arrivalLocation,
                               String departureRoadAddress, String arrivalRoadAddress,
                               LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new Transport(name, trip, transportation, departureLocation, departureRoadAddress, arrivalLocation,arrivalRoadAddress, startDateTime, endDateTime);
    }

    public void updateTransport(String name, String transportation, String departureLocation, String departureRoadAddress,
                                String arrivalLocation, String arrivalRoadAddress,
                                LocalDateTime startDateTime, LocalDateTime endDateTime) {

        super.updateItinerary(name);
        this.transportation = transportation;
        this.departureLocation = departureLocation;
        this.departureRoadAddress = departureRoadAddress;
        this.arrivalLocation = arrivalLocation;
        this.arrivalRoadAddress = arrivalRoadAddress;
        this.departureDateTime = startDateTime;
        this.arrivalDateTime = endDateTime;
    }

    public String getTransportation() {
        return transportation;
    }

    public String getDepartureRoadAddress() {
        return departureRoadAddress;
    }

    public String getArrivalRoadAddress() {
        return arrivalRoadAddress;
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
