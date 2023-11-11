package com.example.trip_itinerary.like.controller;

import com.example.trip_itinerary.like.service.LikeService;
import com.example.trip_itinerary.user.dto.data.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{trip_id}/like")
    public ResponseEntity<HttpStatus> likeTrip(@PathVariable("trip_id") Long tripId, UserId userId) {
        likeService.likeTrip(tripId, userId.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
