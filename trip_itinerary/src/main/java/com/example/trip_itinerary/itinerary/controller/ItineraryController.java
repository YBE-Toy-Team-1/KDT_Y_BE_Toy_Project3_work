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
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "주소 검색", description = "키워드를 통해 정확한 주소를 검색합니다.")
    @GetMapping("/address")
    public ResponseEntity<List<AddressFindResponse>> getAddressByName(
            @Parameter(description = "검색할 키워드", required = true, example = "신라스테이")
            @RequestParam("keyword") String keyword
    ) {
        List<AddressFindResponse> addressResponses = kakaoApiService.getAddress(keyword);

        return ResponseEntity.ok(addressResponses);
    }

    @Operation(summary = "이동 정보 저장", description = "이동 정보를 저장합니다.")
    @PostMapping("/transport")
    public ResponseEntity<HttpStatus> saveTransport(
            @Parameter(description = "이동 정보를 저장할 여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody @Validated TransportSaveRequest transportSaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveTransport(tripId, transportSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @Operation(summary = "체류 정보 저장", description = "체류 정보를 저장합니다.")
    @PostMapping("/stay")
    public ResponseEntity<HttpStatus> saveStay(
            @Parameter(description = "체류 정보를 저장할 여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody @Validated StaySaveRequest staySaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveStay(tripId, staySaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @Operation(summary = "숙박 정보 저장", description = "숙박 정보를 저장합니다.")
    @PostMapping("/accommodation")
    public ResponseEntity<HttpStatus> saveAccommodation(
            @Parameter(description = "숙박 정보를 저장할 여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody @Validated AccommodationSaveRequest accommodationSaveRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.saveAccommodation(tripId, accommodationSaveRequest, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @Operation(summary = "이동 정보 수정", description = "이동 정보를 수정합니다.")
    @PatchMapping("/transport/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchTransport(
            @Parameter(description = "이동 정보를 수정할 여정 ID", required = true, example = "1")
            @PathVariable(name = "itinerary_id") Long itineraryId,
            @RequestBody @Validated TransportUpdateRequest transportPatchRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchTransport(itineraryId, transportPatchRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "체류 정보 수정", description = "체류 정보를 수정합니다.")
    @PatchMapping("/stay/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchStay(
            @Parameter(description = "체류 정보를 수정할 여정 ID", required = true, example = "1")
            @PathVariable(name = "itinerary_id") Long itineraryId,
            @RequestBody @Validated StayUpdateRequest stayUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchStay(itineraryId, stayUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "숙박 정보 수정", description = "숙박 정보를 수정합니다.")
    @PatchMapping("/accommodation/{itinerary_id}")
    public ResponseEntity<HttpStatus> patchAccommodation(
            @Parameter(description = "숙박 정보를 수정할 여정 ID", required = true, example = "1")
            @PathVariable(name = "itinerary_id") Long itineraryId,
            @RequestBody @Validated AccommodationUpdateRequest accommodationUpdateRequest,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        itineraryService.patchAccommodation(itineraryId, accommodationUpdateRequest, memberAdapter);

        return ResponseEntity.noContent().build();
    }

}
