package com.teample.matching.user.service;


import com.teample.matching.user.domain.User;
import com.teample.matching.user.dto.LoginRequestDto;
import com.teample.matching.user.dto.LoginResponseDto;
import com.teample.matching.user.dto.SignupRequestDto;
import com.teample.matching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //1. 회원가입 로직
    @Transactional
    public Long signUp(SignupRequestDto requestDto) {
        validateDuplicateEmail(requestDto.getEmail());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickName(requestDto.getNickName())
                .major(requestDto.getMajor())
                .profile(requestDto.getProfile())
                .temperature(1)
                .build();

        userRepository.save(user);

        return user.getId();

    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }

    //2. 로그인 로직

    // 2. 로그인 로직 (Service 계층)
    public LoginResponseDto login(LoginRequestDto requestDto) {

        // 1. 유저 찾기 (없으면 에러 던지기)
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호를 다시 확인해주세요!"));

        // 2. 비밀번호 체크
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호를 다시 확인해주세요!");
        }

        // 3. 응답 DTO 조립 (빌더 패턴의 정석)
        return LoginResponseDto.builder()
                .token("fake-token1234")
                .message("로그인 성공!!")
                .nickname(user.getNickName())
                .build();
    }

}

