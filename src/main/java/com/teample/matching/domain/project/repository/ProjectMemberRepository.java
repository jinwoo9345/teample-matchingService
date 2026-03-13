package com.teample.matching.domain.project.repository;

import com.teample.matching.domain.project.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findAllByUserId(Long userId);
}
