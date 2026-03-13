package com.teample.matching.domain.matching.controller;

import com.teample.matching.domain.matching.dto.ApplicationRequestDto;
import com.teample.matching.domain.matching.dto.ApplicationResponseDto;
import com.teample.matching.domain.matching.service.ApplicationService;
import com.teample.matching.global.common.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Long>> applyProject(@RequestBody ApplicationRequestDto requestDto) {
        Long applicationId = applicationService.applyProject(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("프로젝트 지원 성공",applicationId));
    }

    // 2. 프로젝트별 지원자 목록 확인 (리더 전용)
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDto>>> getApplications(
            @PathVariable Long projectId,
            @RequestParam Long userId) {

        return ResponseEntity.ok(ApiResponse.success("지원자 조회 성공",applicationService.findAllApplication(projectId, userId)) );
    }
    // 3. 프로젝트 지원자 승인
    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<ApiResponse<Void>> acceptApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {

        applicationService.acceptApplication(applicationId, userId);
        return ResponseEntity.ok(ApiResponse.success("지원자 승인 성공", null));
    }

    // 4. 프로젝트 지원자 거절
    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {

        applicationService.rejectApplication(applicationId, userId);
        return ResponseEntity.ok(ApiResponse.success("지원자 거절 성공", null));
    }

    // 5. 프로젝트 지원 취소
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {

        applicationService.cancelApplication(applicationId, userId);
        return ResponseEntity.ok(ApiResponse.success("지원 취소 성공", null));
    }
}
