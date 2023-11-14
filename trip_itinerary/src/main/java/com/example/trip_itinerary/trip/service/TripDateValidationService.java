package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.request.TripUpdateRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.exception.InvalidDateRangeException;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripDateValidationService {
    public void validateTripSaveDate(TripSaveRequest tripSaveRequest){
        validateTripSaveDateRange(tripSaveRequest);
    }

    public void validateTripPatchDate(TripUpdateRequest tripUpdateRequest, Trip trip){
        validateTripPatchDateRange(tripUpdateRequest);
        validateTripDateForItineraries(trip, tripUpdateRequest.getStartDate(), tripUpdateRequest.getEndDate());
    }

    private void validateTripSaveDateRange(TripSaveRequest tripSaveRequest){
        DateUtil.checkValidDateRange(tripSaveRequest.getStartDate(), tripSaveRequest.getEndDate());
    }

    private void validateTripPatchDateRange(TripUpdateRequest tripUpdateRequest){
        DateUtil.checkValidDateRange(tripUpdateRequest.getStartDate(), tripUpdateRequest.getEndDate());
    }
    
    private void validateTripDateForItineraries(Trip trip, String nowTripStart, String nowTripEnd) {
        List<Itinerary> itineraryList = trip.getItineraryList();
        for (Itinerary itinerary : itineraryList) {
            LocalDate startDate = itinerary.getStartDateTime().toLocalDate();
            LocalDate endDate = itinerary.getEndDateTime().toLocalDate();

            if (startDate.isBefore(DateUtil.toLocalDate(nowTripStart))) {
                throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
            }
            if (endDate.isAfter(DateUtil.toLocalDate(nowTripEnd))) {
                throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
            }
        }
    }

}
