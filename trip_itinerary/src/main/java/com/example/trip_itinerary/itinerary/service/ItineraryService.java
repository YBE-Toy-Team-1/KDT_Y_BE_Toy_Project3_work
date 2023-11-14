package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportUpdateRequest;
import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.example.trip_itinerary.itinerary.exception.ItineraryNotFoundException;
import com.example.trip_itinerary.itinerary.repository.ItineraryRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class  ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;
    private final ItineraryDateTimeValidationService itineraryTimeValidationService;

    public void saveTransport(Long id, TransportSaveRequest request) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        Transport transport = Transport.of(request.getName(), foundTrip, request.getTransportation(), request.getDepartureLocation(),
                request.getDepartureAddress(), request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        itineraryRepository.save(transport);
    }

    public void saveAccommodation(Long id, AccommodationSaveRequest request) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        Accommodation accommodation = Accommodation.of(request.getName(), foundTrip, request.getAccommodationName(),
                request.getAccommodationAddress(), DateUtil.toLocalDateTime(request.getCheckInTime()),
                DateUtil.toLocalDateTime(request.getCheckOutTime()));

        itineraryRepository.save(accommodation);
    }

    public void saveStay(Long id, StaySaveRequest request) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        Stay stay = Stay.of(request.getName(), foundTrip, request.getLocation(), request.getLocationAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()),
                DateUtil.toLocalDateTime(request.getLeaveDateTime()));

        itineraryRepository.save(stay);
    }

    private Trip findTripAndValidateDateTime(Long id, ItinerarySaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        itineraryTimeValidationService.validateItineraryTimeRange(request.getStartDateTime(), request.getEndDateTime(), foundTrip);
        return foundTrip;
    }

    public Long patchTransport(Long id, TransportUpdateRequest request) {
        Transport foundTransport = (Transport) findItineraryAndValidateDateTime(id, request);

        foundTransport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getDepartureAddress(),
                request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        return foundTransport.getTrip().getId();
    }

    public Long patchAccommodation(Long id, AccommodationUpdateRequest request) {
        Accommodation foundAccommodation = (Accommodation) findItineraryAndValidateDateTime(id, request);

        foundAccommodation.updateAccommodation(request.getName(), request.getAccommodationName(),
                                               request.getAccommodationAddress(),
                                               DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));

        return foundAccommodation.getTrip().getId();
    }

    public Long patchStay(Long id, StayUpdateRequest request) {
        Stay foundStay = (Stay) findItineraryAndValidateDateTime(id, request);

        foundStay.updateStay(request.getName(), request.getLocation(), request.getLocationAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()), DateUtil.toLocalDateTime(request.getLeaveDateTime()));

        return foundStay.getTrip().getId();
    }

    private Itinerary findItineraryAndValidateDateTime(Long id, ItineraryUpdateRequest request) {
        Itinerary foundItinerary = itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateItineraryTimeRange(request.getStartDateTime(), request.getEndDateTime(), foundItinerary.getTrip());
        return foundItinerary;
    }


}
