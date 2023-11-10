package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.exception.InvalidDateRangeException;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Long saveTrip(TripSaveRequest tripSaveRequest) {
        DateUtil.checkValidDateRange(tripSaveRequest.getStartDate(), tripSaveRequest.getEndDate());
        return tripRepository.save(tripSaveRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TripListFindResponse> findAllTrips() {
        List<Trip> foundTripList = tripRepository.findAll();

        List<TripListFindResponse> tripFindResponseList = new ArrayList<>();
        for (Trip foundTrip : foundTripList) {

            List<String> itineraryNameList = foundTrip.getItineraryList().stream()
                    .map(Itinerary::getName)
                    .collect(Collectors.toList());

            TripListFindResponse tripListFindResponse = TripListFindResponse.builder()
                    .id(foundTrip.getId())
                    .startDate(foundTrip.getStartDate())
                    .endDate(foundTrip.getEndDate())
                    .isDomestic(foundTrip.isDomestic())
                    .itineraryNameList(itineraryNameList)
                    .build();

            tripFindResponseList.add(tripListFindResponse);
        }

        return tripFindResponseList;
    }

    @Transactional(readOnly = true)
    @Override
    public TripFindResponse getTripById(Long id) {
        Optional<Trip> foundTripOptional = tripRepository.findById(id);
        Trip foundTrip = foundTripOptional.orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        return TripFindResponse.fromEntity(foundTrip);
    }

    @Override
    public Long updateTrip(Long id, TripPatchRequest tripPatchRequest) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        updateRequestDateIfNull(foundTrip, tripPatchRequest);
        DateUtil.checkValidDateRange(tripPatchRequest.getStartDate(), tripPatchRequest.getEndDate());
        validateTripDateForItineraries(foundTrip, tripPatchRequest.getStartDate(), tripPatchRequest.getEndDate());
        foundTrip.updateTrip(tripPatchRequest.getName(), DateUtil.toLocalDate(tripPatchRequest.getStartDate()),
                DateUtil.toLocalDate(tripPatchRequest.getEndDate()), tripPatchRequest.getIsDomestic());
        return foundTrip.getId();
    }

    private void updateRequestDateIfNull(Trip trip, TripPatchRequest tripPatchRequest) {
        if (tripPatchRequest.getStartDate() == null) {
            tripPatchRequest.setStartDate(trip.getStartDate().toString());
        }
        if (tripPatchRequest.getEndDate() == null) {
            tripPatchRequest.setEndDate(trip.getEndDate().toString());
        }
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
