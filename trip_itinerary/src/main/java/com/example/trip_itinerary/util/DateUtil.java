package com.example.trip_itinerary.util;

import com.example.trip_itinerary.itinerary.exception.InvalidDateTimeFormatException;
import com.example.trip_itinerary.itinerary.exception.InvalidDateTimeRangeException;
import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.example.trip_itinerary.trip.exception.InvalidDateFormatException;
import com.example.trip_itinerary.trip.exception.InvalidDateRangeException;
import com.example.trip_itinerary.trip.exception.TripErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public abstract class DateUtil {

    private DateUtil() {
    }

    public static LocalDate toLocalDate(String date) throws InvalidDateFormatException {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(TripErrorCode.INVALID_DATE_FORMAT);
        }
    }

    public static LocalDateTime toLocalDateTime(String dateTime) throws InvalidDateTimeFormatException {
        try {
            return LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException(ItineraryErrorCode.INVALID_DATE_TIME_FORMAT);
        }
    }

    public static void checkValidDateTimeRange(String startDateTimeStr, String endDateTimeStr) {
        LocalDateTime startDateTime = toLocalDateTime(startDateTimeStr);
        LocalDateTime endDateTime = toLocalDateTime(endDateTimeStr);
        if (endDateTime.isBefore(startDateTime)) {
            throw new InvalidDateTimeRangeException(ItineraryErrorCode.INVALID_DATE_TIME_RANGE);
        }
    }

    public static void checkValidDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = toLocalDate(startDateStr);
        LocalDate endDate = toLocalDate(endDateStr);
        if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException(TripErrorCode.INVALID_DATE_RANGE);
        }
    }

    public static void checkRangeStart(String tripStartDateStr, String itineraryStartDateStr) throws InvalidDateRangeException {
        LocalDate tripStartDate = toLocalDate(tripStartDateStr);
        LocalDate itineraryStartDate = toLocalDateTime(itineraryStartDateStr).toLocalDate();

        if (tripStartDate.isAfter(itineraryStartDate)) {
            throw new InvalidDateTimeRangeException(ItineraryErrorCode.INVALID_DATE_TIME_RANGE);
        }
    }


    public static void checkRangeEnd(String tripEndDateStr, String itineraryEndDateStr) throws InvalidDateRangeException {
        LocalDate tripEndDate = toLocalDate(tripEndDateStr);
        LocalDate itineraryEndDate = toLocalDateTime(itineraryEndDateStr).toLocalDate();

        if (tripEndDate.isBefore(itineraryEndDate)) {
            throw new InvalidDateTimeRangeException(ItineraryErrorCode.INVALID_DATE_TIME_RANGE);
        }
    }

}
