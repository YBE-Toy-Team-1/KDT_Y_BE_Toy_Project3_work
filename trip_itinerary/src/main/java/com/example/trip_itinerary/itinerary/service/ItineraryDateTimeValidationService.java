package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportPatchRequest;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItineraryDateTimeValidationService {


    public void validateItinerarySaveTimeRange(ItinerarySaveRequest request, Trip trip){
        DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getStartDateTime());
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getEndDateTime());
        DateUtil.checkValidDateTimeRange(request.getStartDateTime(), request.getEndDateTime());
    }


    public void validateItineraryPatchTimeRange(ItineraryPatchRequest request, Trip trip) {
        DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getStartDateTime());
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getEndDateTime());
        DateUtil.checkValidDateTimeRange(request.getStartDateTime(), request.getEndDateTime());
    }


    public void validateItineraryTimeRange(String startDateTime, String endDateTime, Trip trip){
        DateUtil.checkRangeStart(trip.getStartDate().toString(), startDateTime);
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), endDateTime);
        DateUtil.checkValidDateTimeRange(startDateTime, endDateTime);
    }
}
