package com.teample.matching.domain.project.service;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectRole;
import com.teample.matching.domain.project.dto.*;
import com.teample.matching.domain.project.repository.ProjectRepository;
import com.teample.matching.domain.user.domain.User;
import com.teample.matching.domain.user.repository.UserRepository;
import com.teample.matching.global.exception.NotFoundException;
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
    private final ProjectMemberService projectMemberService;

    // 1. 프로젝트 생성
    @Transactional
    public Long createProject(ProjectCreateRequestDto requestDto) {
        User leader = userRepository.findById(requestDto.getLeaderId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다!"));

        Project project = Project.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .memberRole(requestDto.getMemberRole())
                .deadline(requestDto.getDeadline())
                .capacity(requestDto.getCapacity())
                .period(requestDto.getPeriod())
                .leader(leader)
                .build();

        Project savedProject = projectRepository.save(project);

        // [중요] 프로젝트 생성 시 리더를 멤버 테이블에도 등록!
        projectMemberService.addMember(savedProject, leader , ProjectRole.LEADER);

        return savedProject.getId();
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
                .orElseThrow(()->new NotFoundException("존재하지 않는 프로젝트 입니다!"));
        return new ProjectDetailResponseDto(project);
    }

    // 4. 프로젝트 업데이트
    @Transactional
    public void updateProject(Long id, ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(()->new NotFoundException("존재하지 않는 프로젝트입니다!"));

        project.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getCapacity(),requestDto.getPeriod(),requestDto.getDeadline());
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(()->new NotFoundException("존재하지 않는 프로젝트입니다!"));

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

    public List<ProjectSummaryResponseDto> getJoinedProjects(Long userId) {
        // 내가 리더인 프로젝트
        List<Project> leadingProjects = projectRepository.findByLeaderId(userId);

        // 내가 팀원으로 참여 중인 프로젝트
        List<Project> memberProjects = projectMemberService.findProjectsByUserId(userId);

        return Stream.concat(leadingProjects.stream(), memberProjects.stream())
                .distinct()
                .map(ProjectSummaryResponseDto::new)
                .toList();
    }

}
