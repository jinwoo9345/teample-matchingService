package com.teample.matching.domain.user.service;


import com.teample.matching.domain.matching.dto.ApplicationSummaryResponseDto;
import com.teample.matching.domain.matching.service.ApplicationService;
import com.teample.matching.domain.project.dto.ProjectSummaryResponseDto;
import com.teample.matching.domain.project.service.ProjectService;
import com.teample.matching.domain.tag.domain.Tag;
import com.teample.matching.domain.tag.service.TagService;
import com.teample.matching.domain.user.domain.User;
import com.teample.matching.domain.user.domain.UserTag;
import com.teample.matching.domain.user.dto.*;
import com.teample.matching.domain.user.repository.UserRepository;
import com.teample.matching.global.error.exception.BadRequestException;
import com.teample.matching.global.error.exception.NotFoundException;
import com.teample.matching.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final ApplicationService applicationService;
    private final JwtTokenProvider jwtTokenProvider; // 토큰 생성기
    private final PasswordEncoder passwordEncoder;
    private final TagService tagService;

    //1. 회원가입 로직
    @Transactional
    public Long signUp(SignupRequestDto requestDto) {
        validateDuplicateEmail(requestDto.getEmail());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickName(requestDto.getNickName())
                .major(requestDto.getMajor())
                .profile(requestDto.getProfile())
                .temperature(36)
                .build();

        userRepository.save(user);

        return user.getId();

    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("이미 가입된 이메일입니다.");
        }
    }

    //2. 로그인 로직
    public LoginResponseDto login(LoginRequestDto requestDto) {

        // 1. 유저 찾기 (없으면 에러 던지기)
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("이메일 또는 비밀번호를 다시 확인해주세요!"));

        // 2. 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("이메일 또는 비밀번호를 다시 확인해주세요!");
        }

        // 3. 응답 DTO 조립
        return LoginResponseDto.builder()
                .token(jwtTokenProvider.createToken(user.getId()))
                .message("로그인 성공!!")
                .nickname(user.getNickName())
                .build();
    }

    // 3. 유저 상세보기 (마이페이지)
    public UserMypageResponseDto getUserMypage(Long userId) {

        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 유저입니다!"));

        // 2. 내가 만든 프로젝트 조회 및 변환
       List<ProjectSummaryResponseDto> myProjects = projectService.getProjectsByLeader(userId);

        // 3. 내가 지원한 내역 조회 및 변환
        List<ApplicationSummaryResponseDto> appliedProjects = applicationService.getApplicationByuserId(userId);

        // 4. DTO 생성 및 반환
        return UserMypageResponseDto.builder()
                .user(user)
                .myProjects(myProjects)
                .appliedProjects(appliedProjects)
                .build();
    }

    //4. 유저 상세보기(타인의 상세보기 기능)
    public UserProfileResponseDto getUserProfile(Long currentUserId , Long targetUserId) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다!!"));

        boolean isMe = targetUserId.equals(currentUserId);
        List<ProjectSummaryResponseDto> projects = projectService.getJoinedProjects(targetUserId);

        return UserProfileResponseDto.builder()
                .user(user)
                .projects(projects)
                .isMe(isMe)
                .build();
    }

    // 5. 유저 프로필 업데이트
    @Transactional
    public void updateProfile(UserUpdateProfileRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저가 존재하지 않습니다."));

        user.updateProfile(
                requestDto.getNickName(),
                requestDto.getMajor(),
                requestDto.getProfile(),
                requestDto.getIntroduction()
                );


        List<Tag> tags = tagService.getOrCreateTags(new ArrayList<>(requestDto.getTags()));

        user.getUserTags().clear();

        for (Tag tag : tags) {
            UserTag userTag = UserTag.builder()
                    .user(user)
                    .tag(tag)
                    .build();

            user.addUserTag(userTag);
        }
    }

}

