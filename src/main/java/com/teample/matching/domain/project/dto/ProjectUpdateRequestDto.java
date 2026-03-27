package com.teample.matching.domain.project.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {

    @NotBlank(message = "프로젝트 제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "프로젝트 내용은 필수입니다.")
    private String content;

    @NotEmpty(message = "최소 하나 이상의 태그를 선택해야 합니다.")
    private Set<String> projectTags;

    @Min(value = 2, message = "모집 인원은 최소 2명 이상이어야 합니다.")
    @Max(value = 100, message = "모집 인원은 최대 100명을 넘을 수 없습니다.")
    private int capacity;

    @NotBlank(message = "활동 기간을 입력해주세요.")
    private String period;

    @Future(message = "마감 기한은 현재 시간보다 미래여야 합니다.")
    @NotNull(message = "마감 기한을 설정해주세요.")
    private LocalDateTime deadline;
}