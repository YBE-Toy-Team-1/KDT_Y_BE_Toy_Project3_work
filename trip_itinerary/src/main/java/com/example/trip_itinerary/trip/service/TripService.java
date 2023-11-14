package com.example.trip_itinerary.trip.service;

import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.dto.request.TripUpdateRequest;
import com.example.trip_itinerary.trip.dto.request.TripSaveRequest;
import com.example.trip_itinerary.trip.dto.response.TripFindResponse;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripDateValidationService tripDateValidationService;

    @Transactional
    public Long saveTrip(TripSaveRequest tripSaveRequest) {
        tripDateValidationService.validateTripSaveDate(tripSaveRequest);
        Trip trip = Trip.of(
                tripSaveRequest.getName(),
                DateUtil.toLocalDate(tripSaveRequest.getStartDate()),
                DateUtil.toLocalDate(tripSaveRequest.getEndDate()),
                tripSaveRequest.getIsDomestic()
        );

        return tripRepository.save(trip).getId();
    }

    public List<TripListFindResponse> findAllTrips() {
        List<Trip> foundTripList = tripRepository.findAll();
        return tripListToResponse(foundTripList);
    }

    public TripFindResponse getTripById(Long id) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        return TripFindResponse.fromEntity(foundTrip);
    }

    @Transactional
    public void updateTrip(Long id, TripUpdateRequest tripUpdateRequest) {
        Trip foundTrip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        tripDateValidationService.validateTripPatchDate(tripUpdateRequest, foundTrip);

        foundTrip.updateTrip(tripUpdateRequest.getName(), DateUtil.toLocalDate(tripUpdateRequest.getStartDate()),
                DateUtil.toLocalDate(tripUpdateRequest.getEndDate()), tripUpdateRequest.getIsDomestic());
    }

    public List<TripListFindResponse> searchTrip(String tripName) { // Todo full scan 막아야 하나??
        List<Trip> foundTripList = tripRepository.findByNameContains(tripName);
        return tripListToResponse(foundTripList);
    }

    private List<TripListFindResponse> tripListToResponse(List<Trip> tripList){
        List<TripListFindResponse> tripFindResponseList = new ArrayList<>();
        for (Trip foundTrip : tripList) {

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
}
