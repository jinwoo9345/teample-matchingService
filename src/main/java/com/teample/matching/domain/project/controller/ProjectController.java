package com.teample.matching.domain.project.controller;

import com.teample.matching.domain.project.dto.ProjectCreateRequestDto;
import com.teample.matching.domain.project.dto.ProjectDetailResponseDto;
import com.teample.matching.domain.project.dto.ProjectResponseDto;
import com.teample.matching.domain.project.dto.ProjectUpdateRequestDto;
import com.teample.matching.domain.project.service.ProjectService;
import com.teample.matching.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 1. 프로젝트 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createProject(
            @Valid @RequestBody ProjectCreateRequestDto requestDto,
            @AuthenticationPrincipal Long userId
    ) {
        // 이제 서비스에 userId를 함께 넘겨줍니다.
        Long projectId = projectService.createProject(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("프로젝트 생성 성공", projectId));
    }

    // 2. 전체 프로젝트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> allProjects() {

        List<ProjectResponseDto> projects = projectService.findAllProjects();
        return ResponseEntity.ok(ApiResponse.success("전체 프로젝트 조회 성공", projects));
    }

    // 3. 프로젝트 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDetailResponseDto>> findProjectById( Long id) {
        ProjectDetailResponseDto responseDto = projectService.findProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 상세 조회 성공", responseDto));
    }

    // 4. 프로젝트 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateRequestDto requestDto,
            @AuthenticationPrincipal Long userId
    ) {
        // 본인 확인을 위해 userId를 넘깁니다.
        projectService.updateProject(id, requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 수정 성공", id));
    }

    // 5. 프로젝트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable Long id,
            @AuthenticationPrincipal Long userId // ✨ 삭제를 시도하는 유저 ID
    ) {
        projectService.deleteProject(id, userId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 삭제 성공", null));
    }

    // 6. 프로젝트 모집완료
    @PatchMapping("/{projectId}/completRecruit")
    public ResponseEntity<ApiResponse<Void>> completeRecruit(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long userId
    ){
        projectService.completeMember(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("팀원 모집 마감!",null));
    }

    // 7. 프로젝트 완료 후 종료
    @PatchMapping("/{projectId}/finishProject")
    public ResponseEntity<ApiResponse<Void>> finishProject(
        @PathVariable Long projectId,
        @AuthenticationPrincipal Long userId
    ){
        projectService.finishProject(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트가 종료 되었습니다!",null));
    }



}