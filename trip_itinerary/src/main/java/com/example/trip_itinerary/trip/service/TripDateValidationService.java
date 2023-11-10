package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.exception.InvalidDateRangeException;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

public class TripDateValidationService {
    public void validateTripSaveDate(TripSaveRequest tripSaveRequest){
        validateTripSaveDateRange(tripSaveRequest);
    }

    public void validateTripPatchDate(TripPatchRequest tripPatchRequest, Trip trip){
        validateTripPatchDateRange(tripPatchRequest);
        validateTripDateForItineraries(trip, tripPatchRequest.getStartDate(), tripPatchRequest.getEndDate());
    }

    private void validateTripSaveDateRange(TripSaveRequest tripSaveRequest){
        DateUtil.checkValidDateRange(tripSaveRequest.getStartDate(), tripSaveRequest.getEndDate());
    }

    private void validateTripPatchDateRange(TripPatchRequest tripPatchRequest){
        DateUtil.checkValidDateRange(tripPatchRequest.getStartDate(), tripPatchRequest.getEndDate());
    }

    private void validateTripDateForItineraries(Trip trip, String nowTripStart, String nowTripEnd) {
        List<Itinerary> itineraryList = trip.getItineraryList();
        for (Itinerary i : itineraryList) {
            if (i instanceof Transport) {
                LocalDate departureDate = ((Transport) i).getDepartureDateTime().toLocalDate();
                LocalDate arrivalDate = ((Transport) i).getArrivalDateTime().toLocalDate();

                if (departureDate.isBefore(DateUtil.toLocalDate(nowTripStart))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
                if (arrivalDate.isAfter(DateUtil.toLocalDate(nowTripEnd))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
            } else if (i instanceof Accommodation) {
                LocalDate checkInDate = ((Accommodation) i).getCheckInTime().toLocalDate();
                LocalDate checkOutDate = ((Accommodation) i).getCheckOutTime().toLocalDate();

                if (checkInDate.isBefore(DateUtil.toLocalDate(nowTripStart))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
                if (checkOutDate.isAfter(DateUtil.toLocalDate(nowTripEnd))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
            } else {
                LocalDate arrivalDate = ((Stay) i).getArrivalDateTime().toLocalDate();
                LocalDate leaveDate = ((Stay) i).getLeaveDateTime().toLocalDate();

                if (arrivalDate.isBefore(DateUtil.toLocalDate(nowTripStart))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
                if (leaveDate.isAfter(DateUtil.toLocalDate(nowTripEnd))) {
                    throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
                }
            }

        }
    }

}
