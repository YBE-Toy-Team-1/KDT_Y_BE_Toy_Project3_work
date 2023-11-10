package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.ItinerarySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.ItineraryPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportPatchRequest;
import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.example.trip_itinerary.itinerary.exception.ItineraryNotFoundException;
import com.example.trip_itinerary.itinerary.repository.ItineraryRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;

    private final String TRANSPORT = "transport";
    private final String ACCOMMODATION = "accommodation";
    private final String STAY = "stay";


    public ItineraryService(ItineraryRepository itineraryRepository, TripRepository tripRepository) {
        this.itineraryRepository = itineraryRepository;
        this.tripRepository = tripRepository;
    }

    public Long saveItinerary(Long id, ItinerarySaveRequest itinerarySaveRequest) {
//        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
//
//        Itinerary itinerary;
//        if (itinerarySaveRequest instanceof TransportSaveRequest transportSaveRequest) {
//            itinerary = saveTransport(transportSaveRequest, foundTrip);
//        } else if (itinerarySaveRequest instanceof AccommodationSaveRequest accommodationSaveRequest) {
//            itinerary = saveAccommodation(accommodationSaveRequest, foundTrip);
//        } else {
//            itinerary = saveStay((StaySaveRequest) itinerarySaveRequest, foundTrip);
//        }
//        return itinerary.getId();
    }


    public Transport saveTransport(Long id, TransportSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        this.checkSaveTimeRange(request, foundTrip, TRANSPORT);
        Transport transport = Transport.of(request.getName(), foundTrip, request.getTransportation(), request.getDepartureLocation(),
                request.getArrivalLocation(), DateUtil.toLocalDateTime(request.getDepartureDateTime()),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        return itineraryRepository.save(transport);
    }

    private void checkSaveTimeRange(ItinerarySaveRequest request, Trip trip, String type) {
        if (type.equals(TRANSPORT)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((TransportSaveRequest) request).getDepartureDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((TransportSaveRequest) request).getArrivalDateTime());
            DateUtil.checkValidDateTimeRange(((TransportSaveRequest) request).getDepartureDateTime(), ((TransportSaveRequest) request).getArrivalDateTime());
        } else if (type.equals(ACCOMMODATION)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((AccommodationSaveRequest) request).getCheckInTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((AccommodationSaveRequest) request).getCheckOutTime());
            DateUtil.checkValidDateTimeRange(((AccommodationSaveRequest) request).getCheckInTime(), ((AccommodationSaveRequest) request).getCheckOutTime());
        } else if (type.equals(STAY)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((StaySaveRequest) request).getArrivalDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((StaySaveRequest) request).getLeaveDateTime());
            DateUtil.checkValidDateTimeRange(((StaySaveRequest) request).getArrivalDateTime(), ((StaySaveRequest) request).getLeaveDateTime());
        }
    }

    public Accommodation saveAccommodation(Long id, AccommodationSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        checkSaveTimeRange(request, foundTrip, ACCOMMODATION);
        Accommodation accommodation = Accommodation.of(request.getName(), foundTrip, request.getAccommodationName(),
                DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
        return itineraryRepository.save(accommodation);
    }

    public Stay saveStay(Long id, StaySaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        checkSaveTimeRange(request, foundTrip, STAY);
        Stay stay = Stay.of(request.getName(), foundTrip, request.getLocation(), DateUtil.toLocalDateTime(request.getArrivalDateTime()),
                DateUtil.toLocalDateTime(request.getLeaveDateTime()));
        return itineraryRepository.save(stay);
    }

    public Long patchItinerary(Long id, ItineraryPatchRequest itineraryPatchRequest) {
        Itinerary foundItinerary = itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));

        if (itineraryPatchRequest instanceof TransportPatchRequest transportPatchRequest) {
            patchTransport((Transport) foundItinerary, transportPatchRequest);
        } else if (itineraryPatchRequest instanceof AccommodationPatchRequest accommodationPatchRequest) {
            patchAccommodation((Accommodation) foundItinerary, accommodationPatchRequest);
        } else if (itineraryPatchRequest instanceof StayPatchRequest stayPatchRequest) {
            patchStay((Stay) foundItinerary, stayPatchRequest);
        }

        return foundItinerary.getId();
    }


    public void patchTransport(Transport transport, TransportPatchRequest request) {
        checkPatchTimeRange(request, transport.getTrip(), TRANSPORT);
        transport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getArrivalLocation(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));
    }

    public void patchAccommodation(Accommodation accommodation, AccommodationPatchRequest request) {
        checkPatchTimeRange(request, accommodation.getTrip(), ACCOMMODATION);
        accommodation.updateAccommodation(request.getName(), request.getAccommodationName(),
                DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
    }


    public void patchStay(Stay stay, StayPatchRequest request) {
        checkPatchTimeRange(request, stay.getTrip(), STAY);

        stay.updateStay(request.getName(), request.getLocation(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()), DateUtil.toLocalDateTime(request.getLeaveDateTime()));
    }

    private void checkPatchTimeRange(ItineraryPatchRequest request, Trip trip, String type) {
        if (type.equals(TRANSPORT)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((TransportPatchRequest) request).getDepartureDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((TransportPatchRequest) request).getArrivalDateTime());
            DateUtil.checkValidDateTimeRange(((TransportPatchRequest) request).getDepartureDateTime(), ((TransportPatchRequest) request).getArrivalDateTime());
        } else if (type.equals(ACCOMMODATION)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((AccommodationPatchRequest) request).getCheckInTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((AccommodationPatchRequest) request).getCheckOutTime());
            DateUtil.checkValidDateTimeRange(((AccommodationPatchRequest) request).getCheckInTime(), ((AccommodationPatchRequest) request).getCheckOutTime());
        } else if (type.equals(STAY)) {
            DateUtil.checkRangeStart(trip.getStartDate().toString(), ((StayPatchRequest) request).getArrivalDateTime());
            DateUtil.checkRangeEnd(trip.getEndDate().toString(), ((StayPatchRequest) request).getLeaveDateTime());
            DateUtil.checkValidDateTimeRange(((StayPatchRequest) request).getArrivalDateTime(), ((StayPatchRequest) request).getLeaveDateTime());
        }
    }

}
