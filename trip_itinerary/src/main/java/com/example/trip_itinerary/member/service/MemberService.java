package com.example.trip_itinerary.member.service;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.member.dto.request.LoginRequest;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.exception.EmailAlreadyExistsException;
import com.example.trip_itinerary.member.exception.LoginFailedException;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import com.example.trip_itinerary.member.exception.MemberNotFoundException;
import com.example.trip_itinerary.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        return new MemberAdapter(member);
    }

    @Transactional
    public void registerMember(SignUpRequest signUpRequest) {
        Member member = Member.of(signUpRequest.getName(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        memberRepository.findByEmail(member.getUsername()).ifPresentOrElse(
                it -> {
                    throw new EmailAlreadyExistsException(MemberErrorCode.ALREADY_EMAIL_EXIST);
                },
                () -> {
                    memberRepository.save(member);
                }
        );
    }

    @Transactional
    public String login(LoginRequest loginRequest) {
        Member foundMember = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new LoginFailedException(MemberErrorCode.INVALID_USERNAME_PASSWORD));
        if (passwordEncoder.matches(loginRequest.getPassword(), foundMember.getPassword())) {
            return foundMember.getUsername();
        }
        throw new LoginFailedException(MemberErrorCode.INVALID_USERNAME_PASSWORD);
    }

}
