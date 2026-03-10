package com.teample.matching.project.service;


import com.teample.matching.project.domain.Project;
import com.teample.matching.project.domain.ProjectMember;
import com.teample.matching.project.repository.ProjectMemberRepository;
import com.teample.matching.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;


    // 멤버 추가 (리더/팀원 공통)
    @Transactional
    public void addMember(Project project, User user) {
        project.joinMember();
        ProjectMember member = ProjectMember.createMember(project, user);
        projectMemberRepository.save(member);
    }

    // 유저가 참여 중인 프로젝트 리스트 조회
    public List<Project> findProjectsByUserId(Long userId) {
        return projectMemberRepository.findAllByUserId(userId)
                .stream()
                .map(ProjectMember::getProject)
                .toList();
    }
}


