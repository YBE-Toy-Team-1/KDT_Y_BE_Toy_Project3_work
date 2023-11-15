package com.example.trip_itinerary.like.controller;

import com.example.trip_itinerary.like.service.LikeService;
import com.example.trip_itinerary.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/trips/{trip_id}/like")
    public ResponseEntity<HttpStatus> likeTrip(@PathVariable("trip_id") Long tripId, @AuthenticationPrincipal Member member) {
        likeService.likeTrip(member, tripId);
        return ResponseEntity.ok().build();
    }

}
