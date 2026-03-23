package com.teample.matching.domain.matching.service;

import com.teample.matching.domain.matching.domain.Application;
import com.teample.matching.domain.matching.dto.ApplicationRequestDto;
import com.teample.matching.domain.matching.dto.ApplicationResponseDto;
import com.teample.matching.domain.matching.dto.ApplicationSummaryResponseDto;
import com.teample.matching.domain.matching.repository.ApplicationRepository;
import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectRole;
import com.teample.matching.domain.project.repository.ProjectRepository;
import com.teample.matching.domain.project.service.ProjectMemberService;
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
@Transactional(readOnly = true)
public class ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberService projectMemberService;

    //1. 프로젝트에 지원하기
    @Transactional
    public Long applyProject(ApplicationRequestDto requestDto, Long currentUserId) {

        User user = userRepository.findById(currentUserId).
                orElseThrow(()-> new NotFoundException("유저가 존재하지 않습니다!"));

        Project project =  projectRepository.findById(requestDto.getProjectId())
                .orElseThrow(()->new NotFoundException("프로젝트가 존재하지 않습니다!"));

        if(user.getId().equals(project.getLeader().getId())) {
            throw new BadRequestException("리더는 본인의 프로젝트에 지원할 수 없습니다!");
        }

        // 지원가능 상태체크
        project.validateForApply();

        if(applicationRepository.existsByProjectIdAndUserId(requestDto.getProjectId(), user.getId())) {
           throw new BadRequestException("이미 지원한 프로젝트입니다!!");
        }

        //빌더로 생성
        Application application = Application.builder()
                .user(user)
                .project(project)
                .applyRoles(requestDto.getProjectRoles())
                .introduction(requestDto.getIntroduction())
                .build();

        return applicationRepository.save(application).getId();


    }

    //2. 프로젝트 리더가 지원자 현황 확인
    public List<ApplicationResponseDto> findAllApplication(Long projectId,Long currentUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new NotFoundException("존재하지 않는 프로젝트입니다!!"));


        project.validateLeader(currentUserId);

        List<Application> applications = applicationRepository.findAllByProjectId(projectId);

        return applications.stream()
                .map(ApplicationResponseDto::new)
                .toList();
    }

    //3. 지원서 승인 메소드
    @Transactional
    public void acceptApplication(Long applicationId, Long currentUserId, ProjectRole role) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()->new NotFoundException("지원서를 찾을 수 없습니다!!"));

        Project project = application.getProject();
        User user = application.getUser();

        //리더 권한확인
        project.validateLeader(currentUserId);

        // 프로젝트 상태 검증 및 멤버 추가
        projectMemberService.addMember(project, user, role);

        // 지원서 상태  승인 변경
        application.accept();

    }

    // 4. 지원서 거절 메소드
    @Transactional
    public void rejectApplication(Long applicationId, Long currentUserId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()->new NotFoundException("지원서를 찾을 수 없습니다!!"));

        //리더 권한 확인
        Project project = application.getProject();
        project.validateLeader(currentUserId);

        application.reject();

    }

    // 5. 지원자 지원 취소
    @Transactional
    public void cancelApplication(Long applicationId, Long currentUserId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new NotFoundException("지원서를 찾을 수 없습니다!"));

        if(!application.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException("지원자 본인만 취소 가능합니다!");
        }

        applicationRepository.delete(application);

    }

    @Transactional(readOnly = true)
    public List<ApplicationSummaryResponseDto> getApplicationByuserId(Long userId) {
        return applicationRepository.findAllByUserId(userId)
                .stream()
                .map(ApplicationSummaryResponseDto::new)
                .toList();
    }
}
