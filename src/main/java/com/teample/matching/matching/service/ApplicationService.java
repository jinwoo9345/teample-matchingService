package com.teample.matching.matching.service;

import com.teample.matching.matching.domain.Application;
import com.teample.matching.matching.domain.ApplicationStatus;
import com.teample.matching.matching.dto.ApplicationRequestDto;
import com.teample.matching.matching.dto.ApplicationResponseDto;
import com.teample.matching.matching.repository.ApplicationRepository;
import com.teample.matching.project.domain.Project;
import com.teample.matching.project.repository.ProjectRepository;
import com.teample.matching.user.domain.User;
import com.teample.matching.user.repository.UserRepository;
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

    //1. 프로젝트에 지원하기
    @Transactional
    public Long applyProject(ApplicationRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).
                orElseThrow(()-> new IllegalStateException("유저가 존재하지 않습니다!"));

        Project project =  projectRepository.findById(requestDto.getProjectId())
                .orElseThrow(()->new IllegalArgumentException("프로젝트가 존재하지 않습니다!"));

        if(requestDto.getUserId().equals(project.getLeader().getId())) {
            throw new IllegalArgumentException("리더는 본인의 프로젝트에 지원할 수 없습니다!");
        }

        if(applicationRepository.existsByProjectIdAndUserId(requestDto.getProjectId(), requestDto.getUserId())) {
           throw new IllegalArgumentException("이미 지원한 프로젝트입니다!!");
        }

        if(applicationRepository.countByProjectIdAndStatus(
                requestDto.getProjectId(), ApplicationStatus.ACCEPTED) >= project.getCapacity()){
            throw new IllegalArgumentException("이미 정원이 다 찬 프로젝트입니다!!");
        }

        //이제 빌더로 생성
        Application application = Application.builder()
                .user(user)
                .project(project)
                .introduction(requestDto.getIntroduction())
                .build();

        return applicationRepository.save(application).getId();


    }

    //2. 프로젝트 리더가 지원자 현황 확인
    public List<ApplicationResponseDto> findAllApplication(Long projectId,Long currentUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 프로젝트입니다!!"));


        if(!project.getLeader().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("리더만 지원자를 확인 할 수 있습니다!");
        }

        List<Application> applications = applicationRepository.findAllByProjectId(projectId);

        return applications.stream()
                .map(ApplicationResponseDto::new)
                .toList();
    }

    //3. 지원서 승인 메소드
    @Transactional
    public void acceptApplication(Long applicationId, Long currentUserId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()->new IllegalArgumentException("지원서를 찾을 수 없습니다!!"));

        if(!application.getProject().getLeader().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("승인 권한이 없습니다!");
        }

        long acceptedCount = applicationRepository.countByProjectIdAndStatus(
                application.getProject().getId(), ApplicationStatus.ACCEPTED);

        if (acceptedCount >= application.getProject().getCapacity()) {
            throw new IllegalArgumentException("이미 정원이 가득 차서 더 이상 승인할 수 없습니다.");
        }

        application.accept();

    }

    // 4. 지원서 거절 메소드
    @Transactional
    public void rejectApplication(Long applicationId, Long currentUserId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()->new IllegalArgumentException("지원서를 찾을 수 없습니다!!"));

        if(!application.getProject().getLeader().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("거절 권한이 없습니다!");
        }


        application.reject();

    }

    // 5. 지원자 지원 취소
    @Transactional
    public void cancelApplication(Long applicationId, Long currentUserId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException("지원서를 찾을 수 없습니다!"));

        if(!application.getProject().getLeader().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("지원자 본인만 취소 가능합니다!");
        }

        applicationRepository.delete(application);

    }
}
