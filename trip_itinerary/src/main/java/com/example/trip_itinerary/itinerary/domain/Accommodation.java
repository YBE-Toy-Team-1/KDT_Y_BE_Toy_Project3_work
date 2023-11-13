package com.example.trip_itinerary.itinerary.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("accommodation")
public class Accommodation extends Itinerary {

    @Column(name = "accommodation_name", nullable = false, length = 30)
    private String accommodationName;

    @Column(name = "accommodation_address", nullable = false, length = 50)
    private String accommodationAddress;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    private LocalDateTime checkOutTime;

    protected Accommodation() {
    }

    private Accommodation(String name, Trip trip, String accommodationName, String accommodationAddress, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        super(name, trip);
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public static Accommodation of(String name, Trip trip, String accommodationName,String accommodationAddress,
                                   LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        return new Accommodation(name, trip, accommodationName, accommodationAddress, checkInTime, checkOutTime);
    }

    public void updateAccommodation(String name, String accommodationName, String accommodationAddress, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        super.updateItinerary(name);
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public String getAccommodationAddress() {
        return accommodationAddress;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

}
