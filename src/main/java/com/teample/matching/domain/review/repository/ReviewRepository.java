package com.teample.matching.domain.review.repository;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.review.domain.Review;
import com.teample.matching.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //특정 프로젝트의 모든 리뷰
    List<Review> findByProjectId(Long projectId);

    List<Review> findByTargetUser(User targetUser);

    List<Review> findByProjectIdAndTargetUserId(Long projectId, Long targetUserId);

    // 이미 리뷰가 존재하는지 확인
    boolean existsByProjectAndEvaluatorUserAndTargetUser(Project project, User currentUser, User targetUser);

    // 특정 프로젝트에서 내가 쓴 리뷰들 찾기
    List<Review> findAllByProjectIdAndEvaluatorUserId(Long projectId, Long evaluatorUserId);

}
