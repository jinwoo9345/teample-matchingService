package com.teample.matching.domain.review.service;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectStatus;
import com.teample.matching.domain.project.repository.ProjectRepository;
import com.teample.matching.domain.review.domain.Review;
import com.teample.matching.domain.review.dto.ReviewCreateRequestDto;
import com.teample.matching.domain.review.dto.ReviewDetailResponseDto;
import com.teample.matching.domain.review.dto.ReviewSimpleResponseDto;
import com.teample.matching.domain.review.dto.ReviewableMemberResponseDto;
import com.teample.matching.domain.review.repository.ReviewRepository;
import com.teample.matching.domain.user.domain.User;
import com.teample.matching.domain.user.repository.UserRepository;
import com.teample.matching.global.error.exception.BadRequestException;
import com.teample.matching.global.error.exception.ForbiddenException;
import com.teample.matching.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // 1. 리뷰 작성
    public Long createReview(ReviewCreateRequestDto requestDto, Long projectId, Long currentUserId, Long targetUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없습니다."));

        if (project.getStatus() != ProjectStatus.FINISHED) {
            throw new BadRequestException("종료된 프로젝트만 리뷰 작성이 가능합니다.");
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NotFoundException("리뷰 대상자를 찾을 수 없습니다."));

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new ForbiddenException("본인의 리뷰는 작성할 수 없습니다.");
        }

        // 현재 유저와 대상 유저가 실제 프로젝트 멤버인지 검증
        project.validateMember(currentUserId);
        project.validateMember(targetUserId);

        if (reviewRepository.existsByProjectAndEvaluatorUserAndTargetUser(project, currentUser, targetUser)) {
            throw new ForbiddenException("리뷰는 한 번만 작성할 수 있습니다.");
        }

        Review review = Review.builder()
                .project(project)
                .professionalism(requestDto.getProfessionalism())
                .communication(requestDto.getCommunication())
                .cooperation(requestDto.getCooperation())
                .passion(requestDto.getPassion())
                .punctuality(requestDto.getPunctuality())
                .comment(requestDto.getComment())
                .evaluatorUser(currentUser)
                .targetUser(targetUser)
                .build();

        Review savedReview = reviewRepository.save(review);

        project.addReview(savedReview);

        return savedReview.getId();
    }

    // 2. 리뷰 가능한 멤버 리스트 조회
    @Transactional(readOnly = true)
    public List<ReviewableMemberResponseDto> getReviewableMembers(Long projectId, Long currentUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없습니다."));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // 본인도 해당 프로젝트 멤버인지 확인
        project.validateMember(currentUserId);

        return project.getProjectMembers().stream()
                .filter(member -> !member.getUser().getId().equals(currentUserId))
                .map(member -> {
                    User targetUser = member.getUser();

                    boolean isReviewed = reviewRepository.existsByProjectAndEvaluatorUserAndTargetUser(
                            project,
                            currentUser,
                            targetUser
                    );

                    return ReviewableMemberResponseDto.builder()
                            .userId(targetUser.getId())
                            .nickname(targetUser.getNickName())
                            .profileImage(targetUser.getProfile())
                            .role(member.getProjectRole())
                            .isReviewed(isReviewed)
                            .build();
                })
                .toList();
    }

    // 3. 멤버별 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public List<ReviewSimpleResponseDto> getSimpleReviews(Long projectId, Long targetUserId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없습니다."));

        userRepository.findById(targetUserId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return reviewRepository.findByProjectIdAndTargetUserId(projectId, targetUserId)
                .stream()
                .map(ReviewSimpleResponseDto::new)
                .toList();
    }

    // 4. 리뷰 상세 조회
    @Transactional(readOnly = true)
    public ReviewDetailResponseDto getReviewDetail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("해당 리뷰를 찾을 수 없습니다."));

        return new ReviewDetailResponseDto(review);
    }
}