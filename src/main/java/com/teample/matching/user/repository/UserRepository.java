package com.teample.matching.user.repository;

import com.teample.matching.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 1. 이메일로 가입된 유저가 있는지 확인 (로그인, 중복 검사 시 사용)
    Optional<User> findByEmail(String email);

    // 2. 이메일 중복 여부를 boolean으로 더 빠르게 확인
    boolean existsByEmail(String email);

    // 3. 닉네임 중복 여부 확인
    boolean existsByNickName(String nickName);
}
