package com.teample.matching.global.security.jwt;

import com.teample.matching.global.error.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
    }

    // 토큰 생성
    public String createToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 아이디 설정을 통해 토큰의 해당 유저 파악
                .setIssuedAt(now) // 발행시간
                .setExpiration(expiryDate) // 유효시간
                .signWith(key, SignatureAlgorithm.HS256) // 키값 암호화
                .compact(); // 현재까지의 만든 문자열 통합하여 최종 토큰 완성
    }

    // 토큰에서 유저 ID 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | BadRequestException e) {
            return false;
        }
    }
}