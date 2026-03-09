package com.teample.matching.project.service;

import com.teample.matching.project.domain.Project;
import com.teample.matching.project.domain.ProjectMember;
import com.teample.matching.project.dto.*;
import com.teample.matching.project.repository.ProjectMemberRepository;
import com.teample.matching.project.repository.ProjectRepository;
import com.teample.matching.user.domain.User;
import com.teample.matching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    //1. 프로젝트 생성
    @Transactional
    public Long createProject(ProjectCreateRequestDto requestDto) {

        User leader = userRepository.findById(requestDto.getLeaderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. ID: " + requestDto.getLeaderId()));

        Project project = Project.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .deadline(requestDto.getDeadline())
                .capacity(requestDto.getCapacity())
                .period(requestDto.getPeriod())
                .leader(leader)
                .build();

        return projectRepository.save(project).getId();
    }

    //2. 프로젝트 조회
    public List<ProjectResponseDto> findAllProjects() {
        return projectRepository.findAll().stream() // 1. 전체 엔티티를 흐름(Stream)으로 만든다
                .map(ProjectResponseDto::new)      // 2. 엔티티를 DTO로 변환
                .toList();                         // 3. 변환된 DTO들을 리스트
    }

    // 3. 프로젝트 상세 조회

    public ProjectDetailResponseDto findProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 프로젝트 입니다!"));
        return new ProjectDetailResponseDto(project);
    }

    // 4. 프로젝트 업데이트
    @Transactional
    public void updateProject(Long id, ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 프로젝트입니다!"));

        project.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getCapacity(),requestDto.getPeriod(),requestDto.getDeadline());
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 프로젝트입니다!"));

        projectRepository.delete(project);
    }

    // 유저 아이디가 리더인 프로젝트 찾는 로직
    @Transactional(readOnly = true)
    public List<ProjectSummaryResponseDto> getProjectsByLeader(Long leaderId) {
        return projectRepository.findByLeaderId(leaderId)
                .stream()
                .map(ProjectSummaryResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectSummaryResponseDto> getJoinedProjects(Long userId) {
        // 1. 내가 리더인 프로젝트 조회
        List<Project> leadingProjects = projectRepository.findByLeaderId(userId);

        // 2. 내가 팀원으로 참여 중인 프로젝트 조회 (ProjectMember 거치기)
        List<Project> memberProjects = projectMemberRepository.findAllByUserId(userId)
                .stream()
                .map(ProjectMember::getProject) // ProjectMember 엔티티에서 Project 객체만 추출
                .toList();

        // 3. 두 리스트 합치기
        return Stream.concat(leadingProjects.stream(), memberProjects.stream())
                .distinct() // 리더가 멤버 명단에도 포함되어 있을 경우를 대비
                .map(ProjectSummaryResponseDto::new)
                .toList();
    }

}
