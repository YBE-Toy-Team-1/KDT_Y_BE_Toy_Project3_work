package com.example.trip_itinerary.member.service;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.dto.request.LoginRequest;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.jwt.JwtTokenProvider;
import com.example.trip_itinerary.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다.  " + email));

        return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
    }

    @Transactional
    public void registerMember(SignUpRequest signUpRequest){
        Member member = new Member(signUpRequest.getName(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        memberRepository.findByEmail(member.getUsername()).ifPresentOrElse(
                it -> {
                    throw new RuntimeException();
                },
                () -> {
                    memberRepository.save(member);
                }
        );
    }

    @Transactional
    public String login(LoginRequest loginRequest){
        Member foundMember = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(RuntimeException::new);
        if(passwordEncoder.matches(loginRequest.getPassword(), foundMember.getPassword())){
            return foundMember.getUsername();
        }
        throw new RuntimeException(); // 로그인 실패 exception
    }

}
