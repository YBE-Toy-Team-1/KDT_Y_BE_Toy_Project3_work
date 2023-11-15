package com.example.trip_itinerary.itinerary.controller;

import com.example.trip_itinerary.itinerary.dto.request.save.AccommodationSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.StaySaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.save.TransportSaveRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.AccommodationUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.StayUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.request.update.TransportUpdateRequest;
import com.example.trip_itinerary.itinerary.dto.response.AddressFindResponse;
import com.example.trip_itinerary.itinerary.service.ItineraryService;
import com.example.trip_itinerary.itinerary.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips/{trip_id}")
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final KakaoApiService kakaoApiService;

    @GetMapping("/address/{keyword}")
    public ResponseEntity<List<AddressFindResponse>> getAddressByName(@PathVariable(name = "keyword") String keyword){
        List<AddressFindResponse> addressResponses = kakaoApiService.getAddress(keyword);
        return ResponseEntity.ok(addressResponses);
    }

    @PostMapping("/transport")
    public ResponseEntity<HttpStatus> saveTransport(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated TransportSaveRequest transportSaveRequest) {
        itineraryService.saveTransport(id, transportSaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PostMapping("/stay")
    public ResponseEntity<HttpStatus> saveTransport(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated StaySaveRequest staySaveRequest) {
        itineraryService.saveStay(id, staySaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PostMapping("/accommodation")
    public ResponseEntity<HttpStatus> saveAccommodation(@PathVariable(name = "trip_id") Long id, @RequestBody @Validated AccommodationSaveRequest accommodationSaveRequest) {
        itineraryService.saveAccommodation(id, accommodationSaveRequest);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @PatchMapping("/transport/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchTransport(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated TransportUpdateRequest transportPatchRequest) {
        Long tripId = itineraryService.patchTransport(id, transportPatchRequest);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/stay/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchStay(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated StayUpdateRequest stayUpdateRequest) {
        Long tripId = itineraryService.patchStay(id, stayUpdateRequest);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/accommodation/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchAccommodation(@PathVariable(name = "itinerary_id") Long id, @RequestBody @Validated AccommodationUpdateRequest accommodationUpdateRequest) {
        Long tripId = itineraryService.patchAccommodation(id, accommodationUpdateRequest);

        return ResponseEntity.noContent().build();
    }



}
