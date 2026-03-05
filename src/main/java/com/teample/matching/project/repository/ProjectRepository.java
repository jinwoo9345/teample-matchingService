package com.teample.matching.project.repository;

import com.teample.matching.project.domain.Project;
import com.teample.matching.project.domain.ProjectStatus;
import com.teample.matching.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 1. 최신순 전체 조회 (유저 정보까지 한 번에)
    @EntityGraph(attributePaths = {"leader"})
    List<Project> findAllByOrderByCreatedAtDesc();

    // 2. 모집 상태별 조회 (페이징 적용)
    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);

    // 3. 내가 만든 프로젝트 조회
    List<Project> findByLeader(User leader);

    // 4. 제목 키워드 검색
    List<Project> findByTitleContaining(String title);
}