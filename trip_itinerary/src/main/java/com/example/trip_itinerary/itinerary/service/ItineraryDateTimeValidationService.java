package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportPatchRequest;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.util.DateUtil;

public class ItineraryDateTimeValidationService {
    public void validateTransportSaveTimeRange(TransportSaveRequest request, Trip trip) {
        DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getDepartureDateTime());
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getArrivalDateTime());
        DateUtil.checkValidDateTimeRange(request.getDepartureDateTime(), request.getArrivalDateTime());
    }

    public void validateAccommodationSaveTimeRange(AccommodationSaveRequest request, Trip trip) {
        DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getCheckInTime());
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getCheckOutTime());
        DateUtil.checkValidDateTimeRange(request.getCheckInTime(), request.getCheckOutTime());
    }

    public void validateStaySaveTimeRange(StaySaveRequest request, Trip trip) {
        DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getArrivalDateTime());
        DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getLeaveDateTime());
        DateUtil.checkValidDateTimeRange(request.getArrivalDateTime(), request.getLeaveDateTime());
    }

    public void validateTransportPatchTimeRange(TransportPatchRequest request, Trip trip) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getDepartureDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getArrivalDateTime());
            DateUtil.checkValidDateTimeRange(request.getDepartureDateTime(), request.getArrivalDateTime());
    }

    public void validateAccommodationPatchTimeRange(AccommodationPatchRequest request, Trip trip) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getCheckInTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getCheckOutTime());
            DateUtil.checkValidDateTimeRange(request.getCheckInTime(), request.getCheckOutTime());
    }

    public void validateStayPatchTimeRange(StayPatchRequest request, Trip trip) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), request.getArrivalDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), request.getLeaveDateTime());
            DateUtil.checkValidDateTimeRange(request.getArrivalDateTime(), request.getLeaveDateTime());
    }
}
