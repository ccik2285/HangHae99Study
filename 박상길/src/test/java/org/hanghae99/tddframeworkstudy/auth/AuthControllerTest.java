package org.hanghae99.tddframeworkstudy.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;


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
        UserDto user1 = new User();
        user1.setName(USER_NAME);
        user1.setPassword(USER_PASSWORD);

        // succ
        when(postService.findByName(any())).thenReturn(user1);

        // when
        ResultActions perform = mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // then
        // fail test
        ResultActions perform2 = mockMvc.perform(post(URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("fail"));

    }

}
