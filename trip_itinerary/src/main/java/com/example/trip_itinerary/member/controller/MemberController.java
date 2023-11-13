package com.example.trip_itinerary.member.controller;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.dto.request.LoginRequest;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.dto.response.ApiResponse;
import com.example.trip_itinerary.member.dto.response.JwtAuthenticationResponse;
import com.example.trip_itinerary.member.jwt.JwtTokenProvider;
import com.example.trip_itinerary.member.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "이미 사용중인 이메일 입니다!"), HttpStatus.BAD_REQUEST);
        }


        Member member = new Member(signUpRequest.getName(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        memberRepository.save(member);

        return new ResponseEntity<>(new ApiResponse(true, "회원가입이 성공적으로 완료됐습니다!"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateMember(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );

//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Upon successful authentication, generate a token.
//        String jwt = tokenProvider.generateToken(authentication);
        //Todo 수정필요1

        // Respond with the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(null));
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }
    @PostMapping("test1")
    public String test1(){
        return "test";
    }
}