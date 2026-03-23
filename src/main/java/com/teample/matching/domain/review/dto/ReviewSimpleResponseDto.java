package com.teample.matching.domain.review.dto;

import com.teample.matching.domain.review.domain.Review;

public record ReviewSimpleResponseDto(
        Long id,
        String reviewerNickname,
        Double totalAverageScore,
        String simpleContent
) {
    public ReviewSimpleResponseDto(Review review) {
        this(
                review.getId(),
                review.getEvaluatorUser().getNickName(),
                (
                        review.getProfessionalism()
                                + review.getCommunication()
                                + review.getCooperation()
                                + review.getPassion()
                                + review.getPunctuality()
                ) / 5.0,
                review.getComment()
        );
    }
}