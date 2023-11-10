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
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;

    private final String TRANSPORT = "transport";
    private final String ACCOMMODATION = "accommodation";
    private final String STAY = "stay";


    public ItineraryServiceImpl(ItineraryRepository itineraryRepository, TripRepository tripRepository) {
        this.itineraryRepository = itineraryRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Long saveItinerary(Long id, ItinerarySaveRequest itinerarySaveRequest) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        Itinerary itinerary;
        if (itinerarySaveRequest instanceof TransportSaveRequest transportSaveRequest) {
            itinerary = saveTransport(transportSaveRequest, foundTrip);
        } else if (itinerarySaveRequest instanceof AccommodationSaveRequest accommodationSaveRequest) {
            itinerary = saveAccommodation(accommodationSaveRequest, foundTrip);
        } else {
            itinerary = saveStay((StaySaveRequest) itinerarySaveRequest, foundTrip);
        }
        return itinerary.getId();
    }


    private Transport saveTransport(TransportSaveRequest request, Trip trip) {
        this.
                checkSaveTimeRange(request, trip, TRANSPORT);
        Transport transport = Transport.of(request.getName(), trip, request.getTransportation(), request.getDepartureLocation(),
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

    private Accommodation saveAccommodation(AccommodationSaveRequest request, Trip trip) {
        checkSaveTimeRange(request, trip, ACCOMMODATION);
        Accommodation accommodation = Accommodation.of(request.getName(), trip, request.getAccommodationName(),
                DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
        return itineraryRepository.save(accommodation);
    }

    private Stay saveStay(StaySaveRequest request, Trip trip) {
        checkSaveTimeRange(request, trip, STAY);
        Stay stay = Stay.of(request.getName(), trip, request.getLocation(), DateUtil.toLocalDateTime(request.getArrivalDateTime()),
                DateUtil.toLocalDateTime(request.getLeaveDateTime()));
        return itineraryRepository.save(stay);
    }

    @Override
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


    private void patchTransport(Transport transport, TransportPatchRequest request) {
        if (request.getDepartureDateTime() == null) {
            request.setDepartureDateTime(transport.getDepartureDateTime().toString());
        }
        if (request.getArrivalDateTime() == null) {
            request.setArrivalDateTime(transport.getArrivalDateTime().toString());
        }
        checkPatchTimeRange(request, transport.getTrip(), TRANSPORT);
        transport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getArrivalLocation(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));
    }

    private void patchAccommodation(Accommodation accommodation, AccommodationPatchRequest request) {
        if (request.getCheckInTime() == null) {
            request.setCheckInTime(accommodation.getCheckInTime().toString());
        }
        if (request.getCheckOutTime() == null) {
            request.setCheckOutTime(accommodation.getCheckOutTime().toString());
        }
        checkPatchTimeRange(request, accommodation.getTrip(), ACCOMMODATION);
        accommodation.updateAccommodation(request.getName(), request.getAccommodationName(),
                DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
    }


    private void patchStay(Stay stay, StayPatchRequest request) {
        if (request.getArrivalDateTime() == null) {
            request.setArrivalDateTime(stay.getArrivalDateTime().toString());
        }
        if (request.getLeaveDateTime() == null) {
            request.setLeaveDateTime(stay.getLeaveDateTime().toString());
        }
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
