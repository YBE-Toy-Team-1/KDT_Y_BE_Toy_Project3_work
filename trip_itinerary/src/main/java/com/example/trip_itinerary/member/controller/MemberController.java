package com.example.trip_itinerary.member.controller;

import com.example.trip_itinerary.member.dto.request.LoginRequest;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.dto.response.JwtAuthenticationResponse;
import com.example.trip_itinerary.member.jwt.JwtTokenProvider;
import com.example.trip_itinerary.member.service.MemberService;
import com.example.trip_itinerary.trip.dto.response.TripListFindResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> registerMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.registerMember(signUpRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String userEmail = memberService.login(loginRequest);

        return ResponseEntity.ok(new JwtAuthenticationResponse(tokenProvider.generateToken(userEmail)));
    }

}
