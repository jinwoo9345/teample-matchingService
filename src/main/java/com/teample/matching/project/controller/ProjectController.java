package com.teample.matching.project.controller;


import com.teample.matching.project.dto.ProjectCreateRequestDto;
import com.teample.matching.project.dto.ProjectDetailResponseDto;
import com.teample.matching.project.dto.ProjectResponseDto;
import com.teample.matching.project.dto.ProjectUpdateRequestDto;
import com.teample.matching.project.service.ProjectService;
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

    //1. 프로젝트 생성
    @PostMapping
    public ResponseEntity<Long> createProject(@Valid @RequestBody ProjectCreateRequestDto requestDto) {
        Long projectId = projectService.createProject(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectId);
    }

    // 2.전체 프로젝트 조회
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> allProjects() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    //3. 프로젝트 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailResponseDto> findProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findProjectById(id));
    }

    //4. 프로젝트 수정
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProject(@RequestBody ProjectUpdateRequestDto requestDto, @PathVariable Long id) {
        projectService.updateProject(id, requestDto);
        return ResponseEntity.ok(id);
    }

    // 5. 프로젝트 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

}
