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

    public Transport saveTransport(Long id, TransportSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateTransportSaveTimeRange(request, foundTrip);

        Transport transport = Transport.of(request.getName(), foundTrip, request.getTransportation(), request.getDepartureLocation(),
                request.getDepartureRoadAddress(), request.getArrivalLocation(), request.getArrivalRoadAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()));

        return itineraryRepository.save(transport);
    }

    public Accommodation saveAccommodation(Long id, AccommodationSaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateAccommodationSaveTimeRange(request, foundTrip);

        Accommodation accommodation = Accommodation.of(request.getName(), foundTrip, request.getAccommodationName(),
                request.getRoadAddress(), DateUtil.toLocalDateTime(request.getCheckInTime()),
                DateUtil.toLocalDateTime(request.getCheckOutTime()));
        return itineraryRepository.save(accommodation);
    }

    public Stay saveStay(Long id, StaySaveRequest request) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        itineraryTimeValidationService.validateStaySaveTimeRange(request, foundTrip);

        Stay stay = Stay.of(request.getName(), foundTrip, request.getLocation(), request.getRoadAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()),
                DateUtil.toLocalDateTime(request.getLeaveDateTime()));
        return itineraryRepository.save(stay);
    }

    public void patchTransport(Transport transport, TransportPatchRequest request) {
        Itinerary foundItinerary = itineraryRepository.findById(id).orElseThrow(() -> new ItineraryNotFoundException(ItineraryErrorCode.ITINERARY_NOT_FOUND));
        itineraryTimeValidationService.validateTransportPatchTimeRange(request, transport.getTrip());

        transport.updateTransport(request.getName(), request.getTransportation(),
                request.getDepartureLocation(), request.getDepartureRoadAddress(),
                request.getArrivalLocation(), request.getArrivalRoadAddress(),
                DateUtil.toLocalDateTime(request.getDepartureDateTime()), DateUtil.toLocalDateTime(request.getArrivalDateTime()));
    }

    public void patchAccommodation(Accommodation accommodation, AccommodationPatchRequest request) {
        itineraryTimeValidationService.validateAccommodationPatchTimeRange(request, accommodation.getTrip());

        accommodation.updateAccommodation(request.getName(), request.getAccommodationName(),request.getRoadAddress(),
                DateUtil.toLocalDateTime(request.getCheckInTime()), DateUtil.toLocalDateTime(request.getCheckOutTime()));
    }

    public void patchStay(Stay stay, StayPatchRequest request) {
        itineraryTimeValidationService.validateStayPatchTimeRange(request, stay.getTrip());

        stay.updateStay(request.getName(), request.getLocation(), request.getRoadAddress(),
                DateUtil.toLocalDateTime(request.getArrivalDateTime()), DateUtil.toLocalDateTime(request.getLeaveDateTime()));
    }

    public KakaoAddressResponse getAddressByNameFromKakao(String query) {
        return kakaoApiService.getAddressFromKakao(query);
    }
}
