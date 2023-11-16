package com.example.trip_itinerary.member.service;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.dto.request.SignUpRequest;
import com.example.trip_itinerary.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입 성공")
    void signUpSuccess() {
        Member tester = Member.of("tester", "tester@gasdnaa", passwordEncoder.encode("test123@3@"));
        SignUpRequest request = SignUpRequest.builder()
                .name("tester")
                .email("tester@gasdnaa")
                .password("test123@3@")
                .build();

        given(memberRepository.save(any())).willReturn(tester);

        memberService.registerMember(request);

        Assertions.assertThat(request.getEmail()).isEqualTo(tester.getUsername());
    }

}