package com.teample.matching.domain.user.controller;


import com.teample.matching.domain.user.dto.*;
import com.teample.matching.domain.user.service.UserService;
import com.teample.matching.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //1. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        Long userId = userService.signUp(requestDto);
        // HttpStatus.CREATED는 숫자 201을 의미합니다. (데이터 생성 성공)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 성공",userId));
    }

    //2. 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {

        LoginResponseDto responseDto = userService.login(requestDto);

        return ResponseEntity.ok(ApiResponse.success("로그인 성공",responseDto));
    }

    // 3. 유저 상세보기 (마이페이지)
    @GetMapping("/mypage/{userId}")
    public ResponseEntity< ApiResponse<UserMypageResponseDto>> getMypage(@PathVariable Long userId) {
        UserMypageResponseDto mypageResponseDto = userService.getUserMypage(userId);
        return ResponseEntity.ok(ApiResponse.success("마이페이지 조회 성공!",mypageResponseDto));
    }

    // 4. 유저 상세보기 (타인 프로필)
    // UserController.java 4번 메소드 수정
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserProfile(
            @RequestParam Long currentUserId, // 조회 주체 (current)
            @PathVariable Long userId// 조회 대상 (target)
    ) {
        UserProfileResponseDto userProfileResponseDto = userService.getUserProfile(currentUserId, userId);
        return ResponseEntity.ok(ApiResponse.success("유저 상세조회 성공", userProfileResponseDto));
    }

}
