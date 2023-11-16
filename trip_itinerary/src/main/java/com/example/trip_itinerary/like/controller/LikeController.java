package com.example.trip_itinerary.like.controller;

import com.example.trip_itinerary.like.service.LikeService;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "여행 좋아요 등록/취소", description = "여행 정보에 좋아요를 표시하거나, 취소합니다.")
    @PostMapping("/trips/{trip_id}/like")
    public ResponseEntity<HttpStatus> likeTrip(
            @Parameter(description = "좋아요를 표시하거나 취소할 여행 ID", required = true, example = "1")
            @PathVariable("trip_id") Long tripId,
            @AuthenticationPrincipal MemberAdapter memberAdapter) {
        likeService.likeTrip(memberAdapter, tripId);
        return ResponseEntity.ok().build();
    }

}
