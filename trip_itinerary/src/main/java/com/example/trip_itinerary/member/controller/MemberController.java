package com.example.trip_itinerary.member.controller;

import com.example.trip_itinerary.member.dto.request.LoginRequest;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.dto.response.JwtAuthenticationResponse;
import com.example.trip_itinerary.member.jwt.JwtTokenProvider;
import com.example.trip_itinerary.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider;

    @Operation(summary = "회원가입", description = "서비스에 회원 가입합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> registerMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.registerMember(signUpRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "로그인", description = "서비스에 로그인합니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String userEmail = memberService.login(loginRequest);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return ResponseEntity.ok(new JwtAuthenticationResponse(tokenProvider.generateToken(userEmail, authorities)));
    }

}
