package org.hanghae99.tddframeworkstudy.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtTokenProvider {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 비밀키
    private final long EXPIRATION_TIME = 3600000; // 1시간

    // JWT 토큰 생성
    public String generateToken(String username, Long id) {
        return Jwts.builder()
            .setSubject(username)
            .setId(id.toString())
            .setIssuedAt(new Date()) // 토큰 발행 시간
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 토큰 만료 시간
            .signWith(SECRET_KEY) // 비밀키로 서명
            .compact();
    }

    public UserDto validToken(String token) {
        UserDto userDto = null;
        try{
            Claims body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String id = body.getId();
            String subject = body.getSubject();
            userDto = new UserDto();
            userDto.setId(Long.parseLong(id));
            userDto.setName(subject);
        } catch(Exception e) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        return userDto;
    }

    public static String extractToken(HttpServletRequest request) {
        String token = null;
        try {
            token = request.getHeader("Authorization").substring(7);
        } catch (Exception e) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        return token;
    }
}
