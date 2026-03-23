package com.teample.matching.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 헤더에서 JWT 토큰을 추출합니다.
        String token = resolveToken(request);

        // 2. 토큰이 존재하고 유효한지 검증합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 토큰에서 유저 ID(userId)를 꺼냅니다.
            Long userId = jwtTokenProvider.getUserIdFromToken(token);

            // 4. Spring Security가 인식할 수 있는 인증 객체를 생성합니다.
            // (비밀번호는 보안상 null로 두며, 현재 권한 설정이 없으므로 빈 리스트를 전달합니다.)
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userId, null, List.of()
            );

            // 5. 서버 내 인증 도장(SecurityContext)에 인증 정보를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 6. 다음 필터로 요청을 넘깁니다.
        filterChain.doFilter(request, response);
    }

    // 헤더에서 "Bearer "로 시작하는 토큰 값을 찾아내는 도우미 메소드입니다.
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7); // "Bearer " 이후의 실제 토큰 문자열만 반환
        }
        return null;
    }
}