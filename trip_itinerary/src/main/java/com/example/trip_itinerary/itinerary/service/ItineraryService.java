package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.domain.Accommodation;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.itinerary.domain.Stay;
import com.example.trip_itinerary.itinerary.domain.Transport;
import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayPatchRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportPatchRequest;
import com.example.trip_itinerary.itinerary.dto.response.KakaoAddressResponse;
import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.example.trip_itinerary.itinerary.exception.ItineraryNotFoundException;
import com.example.trip_itinerary.itinerary.repository.ItineraryRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;
    private final ItineraryDateTimeValidationService itineraryTimeValidationService;
    private final KakaoApiService kakaoApiService;

    private final String TRANSPORT = "transport";
    private final String ACCOMMODATION = "accommodation";
    private final String STAY = "stay";


    public void saveTransport(Long id, TransportSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateTransportSaveTimeRange(request, foundTrip);

        Transport transport = Transport.of(request.getName(), foundTrip, request.getTransportation(), request.getDepartureLocation(),
                request.getDepartureAddress(), request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        itineraryRepository.save(transport);
    }

    public void saveAccommodation(Long id, AccommodationSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateAccommodationSaveTimeRange(request, foundTrip);

        Accommodation accommodation = Accommodation.of(request.getName(), foundTrip, request.getAccommodationName(),
                request.getAccommodationAddress(), DateUtil.toLocalDateTime(request.getCheckInTime()),
                DateUtil.toLocalDateTime(request.getCheckOutTime()));

        itineraryRepository.save(accommodation);
    }

    public void saveStay(Long id, StaySaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateStaySaveTimeRange(request, foundTrip);

        Stay stay = Stay.of(request.getName(), foundTrip, request.getLocation(), request.getLocationAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()),
                DateUtil.toLocalDateTime(request.getLeaveDateTime()));
        itineraryRepository.save(stay);
    }

    public Long patchTransport(Long id, TransportPatchRequest request) {
        Transport foundTransport = (Transport) itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateTransportPatchTimeRange(request, foundTransport.getTrip());

        foundTransport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getDepartureAddress(),
                request.getArrivalLocation(), request.getArrivalAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        return foundTransport.getTrip().getId();
    }

    public Long patchAccommodation(Long id, AccommodationPatchRequest request) {
        Accommodation foundAccommodation = (Accommodation) itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateAccommodationPatchTimeRange(request, foundAccommodation.getTrip());

        foundAccommodation.updateAccommodation(request.getName(), request.getAccommodationName(),
                                               request.getAccommodationAddress(),
                                               DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));

        return foundAccommodation.getTrip().getId();
    }

    public Long patchStay(Long id, StayPatchRequest request) {
        Stay foundStay = (Stay) itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateStayPatchTimeRange(request, foundStay.getTrip());

        foundStay.updateStay(request.getName(), request.getLocation(), request.getLocationAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()), DateUtil.toLocalDateTime(request.getLeaveDateTime()));

        return foundStay.getTrip().getId();
    }

    public KakaoAddressResponse getAddressByNameFromKakao(String query) {
        return kakaoApiService.getAddressFromKakao(query);
    }
}
