package com.teample.matching.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {


    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String nickName;
    private String major;
    private String profile;


}
