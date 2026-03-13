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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 1. 프로젝트 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createProject(@Valid @RequestBody ProjectCreateRequestDto requestDto) {
        Long projectId = projectService.createProject(requestDto);
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
    public ResponseEntity<ApiResponse<ProjectDetailResponseDto>> findProjectById(@PathVariable Long id) {
        ProjectDetailResponseDto responseDto = projectService.findProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 상세 조회 성공", responseDto));
    }

    // 4. 프로젝트 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateProject(@RequestBody ProjectUpdateRequestDto requestDto, @PathVariable Long id) {
        projectService.updateProject(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 수정 성공", id));
    }

    // 5. 프로젝트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 삭제 성공", null));
    }

}