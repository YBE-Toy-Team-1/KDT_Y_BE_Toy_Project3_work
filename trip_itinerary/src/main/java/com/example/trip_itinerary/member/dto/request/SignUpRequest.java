package com.example.trip_itinerary.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일 주소는 필수 입력 해주세요")
    @Email(message = "형식에 맞는 이메일 주소를 입력 해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 해주세요")
    @Size(min = 8, max = 24, message = "비밀번호는 8자 이상 24자 이하로 입력해주세요")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).*$", message = "비밀번호는 숫자, 영문자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 해주세요")
    @Size(min = 2, max = 24, message = "이름은 2자 이상 24자 이하로 입력해주세요")
    private String name;

    @Builder
    public SignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
