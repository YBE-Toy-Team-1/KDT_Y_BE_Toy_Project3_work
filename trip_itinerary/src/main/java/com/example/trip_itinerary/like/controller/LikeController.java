package com.example.trip_itinerary.like.controller;

import com.example.trip_itinerary.like.service.LikeService;
import com.example.trip_itinerary.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "여행 좋아요 등록/취소")
    @PostMapping("/trips/{trip_id}/like")
    public ResponseEntity<HttpStatus> likeTrip(@PathVariable("trip_id") Long tripId) {
        Member tester = new Member(2L);
        likeService.likeTrip(tester, tripId);
        return ResponseEntity.ok().build();
    }

}
