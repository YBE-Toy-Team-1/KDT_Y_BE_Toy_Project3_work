package com.example.trip_itinerary.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "형식에 맞는 이메일 주소를 입력 해주세요.")
    @NotBlank(message = "이메일 주소를 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
