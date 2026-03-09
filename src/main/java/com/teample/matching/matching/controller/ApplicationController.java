package com.teample.matching.matching.controller;

import com.teample.matching.matching.dto.ApplicationRequestDto;
import com.teample.matching.matching.dto.ApplicationResponseDto;
import com.teample.matching.matching.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    //1. 프로젝트 지원
    @PostMapping
    public ResponseEntity<Long> applyProject(@RequestBody ApplicationRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.applyProject(requestDto));
    }

    // 2. 프로젝트별 지원자 목록 확인 (리더 전용)
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ApplicationResponseDto>> getApplications(
            @PathVariable Long projectId,
            @RequestParam Long userId) {

        return ResponseEntity.ok(applicationService.findAllApplication(projectId, userId));
    }
    // 3. 프로젝트 지원자 승인
    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<Void> acceptApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {
        applicationService.acceptApplication(applicationId, userId);
        return ResponseEntity.noContent().build();
    }

    // 4. 프로젝트 지원자 거절
    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<Void> rejectApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {

        applicationService.rejectApplication(applicationId, userId);
        return ResponseEntity.noContent().build();
    }

    // 5. 프로젝트 지원취소
    @DeleteMapping
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {
        applicationService.cancelApplication(applicationId, userId);
        return ResponseEntity.noContent().build();
    }
}
