package com.teample.matching.matching.repository;


import com.teample.matching.matching.domain.Application;
import com.teample.matching.matching.domain.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // 지원서 수정 취소 상세보기용
    Optional<Application> findByProjectIdAndUserId(Long projectId, Long userId);

    // 1. 특정 프로젝트의 지원서 목록 조회 (리더가 명단을 볼 때 사용)
    List<Application> findAllByProjectId(Long projectId);

    // 2. 중복 지원 방지 (이미 지원했는지 확인용)
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

    // 3. [지원자용] 내가 지원한 모든 지원서 목록 조회
    // 마이페이지 같은 곳에서 "내가 어디어디 지원했지?"를 보여줄 때 필수
    List<Application> findAllByUserId(Long userId);

    // 4. [리더/시스템용] 현재 프로젝트에 '승인'된 인원이 몇 명인지 카운트
    // 모집 정원 고려
    long countByProjectIdAndStatus(Long projectId, ApplicationStatus status);

    // 5. [조회용] 특정 프로젝트의 지원서 중 '대기(PENDING)' 중인 것만 골라보기
    // 리더가 이미 거절하거나 수락한 사람 제외
    List<Application> findAllByProjectIdAndStatus(Long projectId, ApplicationStatus status);
}
