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
import com.example.trip_itinerary.member.domain.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "주소 검색")
    @GetMapping("/address/{keyword}")
    public ResponseEntity<List<AddressFindResponse>> getAddressByName(@PathVariable(name = "keyword") String keyword){
        List<AddressFindResponse> addressResponses = kakaoApiService.getAddress(keyword);
        return ResponseEntity.ok(addressResponses);
    }

    @Operation(summary = "이동 정보 저장")
    @PostMapping("/transport")
    public ResponseEntity<HttpStatus> saveTransport(
            @PathVariable(name = "trip_id") Long id,
            @RequestBody @Validated TransportSaveRequest transportSaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveTransport(id, transportSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @Operation(summary = "체류 정보 저장")
    @PostMapping("/stay")
    public ResponseEntity<HttpStatus> saveStay(
            @PathVariable(name = "trip_id") Long id,
            @RequestBody @Validated StaySaveRequest staySaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveStay(id, staySaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @Operation(summary = "숙박 정보 저장")
    @PostMapping("/accommodation")
    public ResponseEntity<HttpStatus> saveAccommodation(
            @PathVariable(name = "trip_id") Long id,
            @RequestBody @Validated AccommodationSaveRequest accommodationSaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveAccommodation(id, accommodationSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

    @Operation(summary = "이동 정보 수정")
    @PatchMapping("/transport/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchTransport(
            @PathVariable(name = "itinerary_id") Long id,
            @RequestBody @Validated TransportUpdateRequest transportPatchRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchTransport(id, transportPatchRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "체류 정보 수정")
    @PatchMapping("/stay/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchStay(
            @PathVariable(name = "itinerary_id") Long id,
            @RequestBody @Validated StayUpdateRequest stayUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchStay(id, stayUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "숙박 정보 수정")
    @PatchMapping("/accommodation/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchAccommodation(
            @PathVariable(name = "itinerary_id") Long id,
            @RequestBody @Validated AccommodationUpdateRequest accommodationUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchAccommodation(id, accommodationUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

}
