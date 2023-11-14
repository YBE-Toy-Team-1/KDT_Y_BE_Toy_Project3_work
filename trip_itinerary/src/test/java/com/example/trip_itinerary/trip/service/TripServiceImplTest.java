package com.example.trip_itinerary.trip.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {
//    @Mock
//    TripRepository tripRepository;
//    @InjectMocks
//    TripService tripService;
//
//    @Test
//    void saveTrip() {
//        Trip savedTrip = Trip.of(1L, "testName", LocalDate.now(), LocalDate.now(), false, null);
//        given(tripRepository.save(any()))
//                .willReturn(savedTrip);
//        TripSaveRequest requestTrip = TripSaveRequest.builder()
//                .name("testName")
//                .startDate(LocalDate.now().toString())
//                .endDate(LocalDate.now().toString())
//                .isDomestic(false)
//                .build();
//        Long savedId = tripService.saveTrip(requestTrip);
//        Assertions.assertThat(savedTrip.getName()).isEqualTo(requestTrip.getName());
//    }
//
//    @Test
//    void findAllTrips() {
//        List<Trip> tripList = new ArrayList<>();
//        tripList.add(Trip.of(1L, "test1", LocalDate.now(), LocalDate.now(), false, new ArrayList<>()));
//        tripList.add(Trip.of(2L, "test2", LocalDate.now(), LocalDate.now(), true, new ArrayList<>()));
//        given(tripRepository.findAll())
//                .willReturn(tripList);
//
//        List<TripListFindResponse> allTrips = tripService.findAllTrips();
//        Assertions.assertThat(allTrips.size()).isEqualTo(2);
//        Assertions.assertThat(allTrips.get(0).getId()).isEqualTo(1L);
//
//    }
//
//    @Test
//    void getTripById() {
//        Trip trip = Trip.of(1L, "test1", LocalDate.now(), LocalDate.now(), false, new ArrayList<>());
//        given(tripRepository.findById(any()))
//                .willReturn(Optional.of(trip));
//        TripFindResponse foundTrip = tripService.getTripById(1L);
//        Assertions.assertThat(foundTrip.getName()).isEqualTo(trip.getName());
//    }
//
//    @Test
//    void patchTrip() {
//        Trip trip = Trip.of(1L, "test1", LocalDate.now(), LocalDate.now(), false, new ArrayList<>());
//        given(tripRepository.findById(any()))
//                .willReturn(Optional.of(trip));
//
//        TripPatchRequest requestTrip = TripPatchRequest.builder()
//                .name("test1")
//                .startDate(LocalDate.now().toString())
//                .endDate(LocalDate.now().toString())
//                .isDomestic(false)
//                .build();
//        Long id = tripService.updateTrip(1L, requestTrip);
//
//        Assertions.assertThat(id).isEqualTo(1L);
//    }
}