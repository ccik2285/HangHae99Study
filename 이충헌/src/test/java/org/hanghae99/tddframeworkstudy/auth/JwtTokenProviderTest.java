package org.hanghae99.tddframeworkstudy.auth;

import org.hanghae99.tddframeworkstudy.web.security.util.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 토큰생성테스트() {
        // Given
        String username = "testuser";

        // When
        String token = jwtTokenProvider.generateToken(username);

        // Then
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(username, jwtTokenProvider.getUsernameFromToken(token));
    }

    @Test
    void 토큰실패테스트() {
        // Given
        String invalidToken = "invalid.token.string";

        // When & Then
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
}
