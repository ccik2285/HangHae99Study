package org.hanghae99.tddframeworkstudy.auth;

import static org.assertj.core.api.Assertions.assertThat;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtTokenTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private final Long USER_ID = 1L;
    private final String USER_NAME = "test1234";
    private final String USER_PASSWORD = "test";

    @Test
    @DisplayName("토큰생성 test")
    public void tokenGeneratedTest() {

        // given
        User user = new User();
        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setPassword(USER_PASSWORD);

        SecretKey secretKey = jwtTokenProvider.getSECRET_KEY();

        // when
        String jwtToken = jwtTokenProvider.generateToken(user.getName(), user.getId());

        // then
        assertThat(jwtToken).isNotBlank();

        // given
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

        // then
        assertThat(claims.getSubject()).isEqualTo(USER_NAME);
    }

}
