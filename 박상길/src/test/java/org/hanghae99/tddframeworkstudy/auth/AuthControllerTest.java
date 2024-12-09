package org.hanghae99.tddframeworkstudy.auth;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.hanghae99.tddframeworkstudy.user.User;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.hanghae99.tddframeworkstudy.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long USER_ID = 1L;
    private final String USER_NAME = "test";
    private final String USER_PASSWORD = "test";

    private final String USER_NAME2 = "test2";

    /**
     * username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성
     * password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
     * @throws Exception
     */
    @Test
    @DisplayName("회원가입 api")
    public void signUp() throws Exception {
        final String URL = "/signUp";

        // given
        UserDto user1 = new UserDto();
        user1.setName(USER_NAME);
        user1.setPassword(USER_PASSWORD);

        // when
        ResultActions perform = mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());

        // then
        // fail test
        ResultActions perform2 = mockMvc.perform(post(URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("error"));

    }

    /**
     * 1. username, password를 Client에서 전달받기
     * 2.  로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
     *      발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
     * @throws Exception
     */
    @Test
    @DisplayName("로그인 api")
    public void signIn() throws Exception {
        final String URL = "/signIn";

        // given
        UserDto user1 = new UserDto();
        user1.setId(USER_ID);
        user1.setName(USER_NAME);
        user1.setPassword(USER_PASSWORD);

        // when - 1
        ResultActions perform = mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON))
            // then - 1
            .andExpect(status().isOk());

        // then - 1
        // fail test
        ResultActions perform2 = mockMvc.perform(post(URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("error"));


        // when - 2
        // return 동작이 아닌 response header에 값을 넣어줘야 하기때문에 필요함
        // then 동작을 통해 강제로 token 주입 한 뒤 userDto를 반환
        when(authService.signIn(any(), any())).then(invocationOnMock -> {
            HttpServletResponse argument = invocationOnMock.getArgument(1);
            argument.setHeader("Authorization", "Bearer " + "test");
            return user1;
        });

        ResultActions perform3 = mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON))
            // then - 2
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(header().string("Authorization", "Bearer " + "test"));
    }


}
