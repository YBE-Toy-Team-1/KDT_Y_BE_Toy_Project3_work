package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.itinerary.domain.Itinerary;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.request.TripPatchRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final TripDateValidationService tripDateValidationService;

    public TripService(TripRepository tripRepository, TripDateValidationService tripDateValidationService) {
        this.tripRepository = tripRepository;
        this.tripDateValidationService = tripDateValidationService;
    }

    public Long saveTrip(TripSaveRequest tripSaveRequest) {
        tripDateValidationService.validateTripSaveDate(tripSaveRequest);

        return tripRepository.save(tripSaveRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<TripListFindResponse> findAllTrips() {
        List<Trip> foundTripList = tripRepository.findAll();

        List<TripListFindResponse> tripFindResponseList = new ArrayList<>();
        for (Trip foundTrip : foundTripList) {

            TripListFindResponse tripListFindResponse = TripListFindResponse.builder()
                    .id(foundTrip.getId())
                    .startDate(foundTrip.getStartDate())
                    .endDate(foundTrip.getEndDate())
                    .isDomestic(foundTrip.isDomestic())
                    .build();

            tripFindResponseList.add(tripListFindResponse);
        }

        return tripFindResponseList;
    }

    @Transactional(readOnly = true)
    public TripFindResponse getTripById(Long id) {
        Optional<Trip> foundTripOptional = tripRepository.findById(id);
        Trip foundTrip = foundTripOptional.orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        return TripFindResponse.fromEntity(foundTrip);
    }

    public Long updateTrip(Long id, TripPatchRequest tripPatchRequest) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        tripDateValidationService.validateTripPatchDate(tripPatchRequest, foundTrip);

        foundTrip.updateTrip(tripPatchRequest.getName(), DateUtil.toLocalDate(tripPatchRequest.getStartDate()),
                DateUtil.toLocalDate(tripPatchRequest.getEndDate()), tripPatchRequest.getIsDomestic());
        return foundTrip.getId();
    }
}
