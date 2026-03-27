package com.teample.matching.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class UserUpdateProfileRequestDto {

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
    private String nickName;

    private String major;

    @Size(max = 500, message = "자기소개는 최대 500자까지 입력 가능합니다.")
    private String introduction;

    private String profile;

    @NotNull(message = "태그 목록은 null일 수 없습니다. (빈 배열[]은 가능)")
    @Size(max = 10, message = "관심 태그는 최대 10개까지만 설정할 수 있습니다.")
    private Set<String> tags;
}