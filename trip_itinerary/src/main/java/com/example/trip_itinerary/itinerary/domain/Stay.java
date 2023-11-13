package com.example.trip_itinerary.itinerary.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("stay")
public class Stay extends Itinerary {

    @Column(nullable = false, length = 30)
    private String location;

    @Column(name = "road_address", nullable = false, length = 30)
    private String roadAddress;

    @Column(nullable = false, name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

    @Column(nullable = false, name = "leave_date_time")
    private LocalDateTime leaveDateTime;

    protected Stay() {
    }

    private Stay(String name, Trip trip, String location, String roadAddress, LocalDateTime arrivalDateTime, LocalDateTime leaveDateTime) {
        super(name, trip);
        this.location = location;
        this.roadAddress = roadAddress;
        this.arrivalDateTime = arrivalDateTime;
        this.leaveDateTime = leaveDateTime;
    }

    public static Stay of(String name, Trip trip, String location, String roadAddress, LocalDateTime arrivalDateTime, LocalDateTime leaveDateTime) {
        return new Stay(name, trip, location, roadAddress, arrivalDateTime, leaveDateTime);
    }

    public void updateStay(String name, String location, String roadAddress, LocalDateTime arrivalDateTime, LocalDateTime leaveDateTime) {
        super.updateItinerary(name);
        this.location = location;
        this.roadAddress = roadAddress;
        this.arrivalDateTime = arrivalDateTime;
        this.leaveDateTime = leaveDateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public LocalDateTime getLeaveDateTime() {
        return leaveDateTime;
    }

}
