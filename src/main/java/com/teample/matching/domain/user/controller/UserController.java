package com.teample.matching.domain.user.controller;


import com.teample.matching.domain.user.dto.*;
import com.teample.matching.domain.user.service.UserService;
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
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signUp(requestDto);

        // HttpStatus.CREATED는 숫자 201을 의미합니다. (데이터 생성 성공)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
    }

    //2. 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {

        LoginResponseDto responseDto = userService.login(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 3. 유저 상세보기 (마이페이지)
    @GetMapping("/mypage/{userId}")
    public ResponseEntity<UserMypageResponseDto> getMypage(@PathVariable Long userId) {
        UserMypageResponseDto mypageResponseDto = userService.getUserMypage(userId);
        return ResponseEntity.ok(mypageResponseDto);
    }

    // 4. 유저 상세보기 (타인 프로필)
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(
            @PathVariable Long userId, // 조회 대상
            @RequestParam Long currentUserId // 조회 주체
    ) {
        return ResponseEntity.ok(userService.getUserProfile(currentUserId, userId));
    }

}
