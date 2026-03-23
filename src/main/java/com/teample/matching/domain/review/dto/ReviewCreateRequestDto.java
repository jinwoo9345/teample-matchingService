package com.teample.matching.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequestDto {

    @Min(value = 1, message = "전문성 점수는 1 이상이어야 합니다.")
    @Max(value = 100, message = "전문성 점수는 100 이하여야 합니다.")
    private int professionalism;

    @Min(value = 1, message = "소통 점수는 1 이상이어야 합니다.")
    @Max(value = 100, message = "소통 점수는 100 이하여야 합니다.")
    private int communication;

    @Min(value = 1, message = "시간 엄수 점수는 1 이상이어야 합니다.")
    @Max(value = 100, message = "시간 엄수 점수는 100 이하여야 합니다.")
    private int punctuality;

    @Min(value = 1, message = "협업 점수는 1 이상이어야 합니다.")
    @Max(value = 100, message = "협업 점수는 100 이하여야 합니다.")
    private int cooperation;

    @Min(value = 1, message = "열정 점수는 1 이상이어야 합니다.")
    @Max(value = 100, message = "열정 점수는 100 이하여야 합니다.")
    private int passion;

    @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
    @Size(max = 1000, message = "리뷰 내용은 1000자 이하여야 합니다.")
    private String comment;
}