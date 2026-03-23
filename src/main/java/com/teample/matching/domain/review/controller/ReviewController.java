package com.teample.matching.domain.review.controller;

import com.teample.matching.domain.review.dto.ReviewCreateRequestDto;
import com.teample.matching.domain.review.dto.ReviewDetailResponseDto;
import com.teample.matching.domain.review.dto.ReviewSimpleResponseDto;
import com.teample.matching.domain.review.dto.ReviewableMemberResponseDto;
import com.teample.matching.domain.review.service.ReviewService;
import com.teample.matching.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 리뷰 생성
    @PostMapping("/projects/{projectId}/members/{targetUserId}/reviews")
    public ResponseEntity<ApiResponse<Long>> create(
            @Valid @RequestBody ReviewCreateRequestDto reviewCreateRequestDto,
            @PathVariable Long projectId,
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal Long currentUserId
    ) {
        Long reviewId = reviewService.createReview(
                reviewCreateRequestDto,
                projectId,
                currentUserId,
                targetUserId
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("리뷰가 작성되었습니다.", reviewId));
    }

    // 2. 리뷰 가능한 멤버 조회
    @GetMapping("/projects/{projectId}/reviews/members")
    public ResponseEntity<ApiResponse<List<ReviewableMemberResponseDto>>> getReviewableMembers(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long currentUserId
    ) {
        List<ReviewableMemberResponseDto> reviewableMembers =
                reviewService.getReviewableMembers(projectId, currentUserId);

        return ResponseEntity.ok(
                ApiResponse.success("리뷰 멤버 조회 성공", reviewableMembers)
        );
    }

    // 3. 멤버별 전체 리뷰 조회
    @GetMapping("/projects/{projectId}/members/{targetUserId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewSimpleResponseDto>>> getTargetMemberReview(
            @PathVariable Long projectId,
            @PathVariable Long targetUserId
    ) {
        List<ReviewSimpleResponseDto> simpleReviews =
                reviewService.getSimpleReviews(projectId, targetUserId);

        return ResponseEntity.ok(
                ApiResponse.success("타겟 멤버 전체 리뷰 조회 성공", simpleReviews)
        );
    }

    // 4. 특정 리뷰 상세 조회
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewDetailResponseDto>> getReviewDetail(
            @PathVariable Long reviewId
    ) {
        ReviewDetailResponseDto reviewDetailResponseDto =
                reviewService.getReviewDetail(reviewId);

        return ResponseEntity.ok(
                ApiResponse.success("리뷰 상세 조회 성공", reviewDetailResponseDto)
        );
    }
}