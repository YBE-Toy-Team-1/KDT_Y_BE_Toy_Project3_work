package com.example.trip_itinerary.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size //todo min max 지정
    private String password;

    @NotBlank
    private String name;

}
