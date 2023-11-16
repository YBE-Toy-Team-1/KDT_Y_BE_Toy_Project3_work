package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.util.DateUtil;
import org.springframework.stereotype.Service;

@Service
public class ItineraryDateTimeValidationService {
    public void validateItineraryTimeRange(String startDateTime, String endDateTime, Trip trip) {
        DateUtil.checkRangeStart(trip.getStartDate().toString(), startDateTime);
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), endDateTime);
        DateUtil.checkValidDateTimeRange(startDateTime, endDateTime);
    }
}
