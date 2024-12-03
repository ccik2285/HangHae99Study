package org.hanghae99.tddframeworkstudy.auth;

import static org.assertj.core.api.Assertions.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
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

    private final String USER_NAME = "test1234";
    private final String USER_PASSWORD = "test";

    @Test
    @DisplayName("토큰생성 test")
    public void tokenGeneratedTest() {

        // given
        User user = new User();
        user.setName(USER_NAME);
        user.setPassword(USER_PASSWORD);

        String key = jwtTokenProvider.getKey();

        // when
        String jwtToken = jwtTokenProvider.generateJwt(user.getName());

        // then
        assertThat(jwtToken).isNotBlank();


        // given
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();

        // then
        assertThat(claims.getSubject()).isEqualTo(USER_NAME);

    }

}
