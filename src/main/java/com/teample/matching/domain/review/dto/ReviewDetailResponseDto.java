package com.teample.matching.domain.review.dto;

import com.teample.matching.domain.review.domain.Review;

import java.time.LocalDateTime;

public record ReviewDetailResponseDto(
        Long id,
        String reviewerNickName,
        String reviewerProfile,
        Integer professionalism,
        Integer communication,
        Integer cooperation,
        Integer passion,
        Integer punctuality,
        Double averageScore,
        String comment,
        LocalDateTime createdAt
) {
    // Review 엔티티를 DTO로 변환하는 생성자
    public ReviewDetailResponseDto(Review review) {
        this(
                review.getId(),
                review.getEvaluatorUser().getNickName(),
                review.getEvaluatorUser().getProfile(),
                review.getProfessionalism(),
                review.getCommunication(),
                review.getCooperation(),
                review.getPassion(),
                review.getPunctuality(),
                (review.getProfessionalism() + review.getCommunication() +
                        review.getCooperation() + review.getPassion() + review.getPunctuality()) / 5.0,
                review.getComment(),
                review.getCreatedAt()
        );
    }
}