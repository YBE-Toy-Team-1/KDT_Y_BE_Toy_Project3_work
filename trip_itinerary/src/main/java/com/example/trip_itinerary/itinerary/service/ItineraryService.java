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
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import com.example.trip_itinerary.member.exception.MemberNotMatchedException;
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

    public void saveTransport(Long id, TransportSaveRequest request, MemberAdapter memberAdapter) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundTrip.getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

        Transport transport = Transport.of(request.getName(), foundTrip, request.getTransportation(), request.getDepartureLocation(),
                request.getDepartureAddress(), request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        itineraryRepository.save(transport);
    }

    public void saveAccommodation(Long id, AccommodationSaveRequest request, MemberAdapter memberAdapter) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundTrip.getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

        Accommodation accommodation = Accommodation.of(request.getName(), foundTrip, request.getAccommodationName(),
                request.getAccommodationAddress(), DateUtil.toLocalDateTime(request.getCheckInTime()),
                DateUtil.toLocalDateTime(request.getCheckOutTime()));

        itineraryRepository.save(accommodation);
    }

    public void saveStay(Long id, StaySaveRequest request, MemberAdapter memberAdapter) {
        Trip foundTrip = findTripAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundTrip.getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

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

    public void patchTransport(Long id, TransportUpdateRequest request, MemberAdapter memberAdapter) {
        Transport foundTransport = (Transport) findItineraryAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundTransport.getTrip().getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

        foundTransport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getDepartureAddress(),
                request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));
    }

    public void patchAccommodation(Long id, AccommodationUpdateRequest request, MemberAdapter memberAdapter) {
        Accommodation foundAccommodation = (Accommodation) findItineraryAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundAccommodation.getTrip().getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

        foundAccommodation.updateAccommodation(request.getName(), request.getAccommodationName(),
                                               request.getAccommodationAddress(),
                                               DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
    }

    public void patchStay(Long id, StayUpdateRequest request, MemberAdapter memberAdapter) {
        Stay foundStay = (Stay) findItineraryAndValidateDateTime(id, request);

        if (!memberAdapter.getUsername().equals(foundStay.getTrip().getMember().getUsername())) {
            throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
        }

        foundStay.updateStay(request.getName(), request.getLocation(), request.getLocationAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()), DateUtil.toLocalDateTime(request.getLeaveDateTime()));

    }

    private Itinerary findItineraryAndValidateDateTime(Long id, ItineraryUpdateRequest request) {
        Itinerary foundItinerary = itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateItineraryTimeRange(request.getStartDateTime(), request.getEndDateTime(), foundItinerary.getTrip());
        return foundItinerary;
    }


}
