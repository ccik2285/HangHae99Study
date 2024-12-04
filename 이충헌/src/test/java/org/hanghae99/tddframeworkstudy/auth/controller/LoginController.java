package org.hanghae99.tddframeworkstudy.auth.controller;


import org.hanghae99.tddframeworkstudy.userjoin.entity.Member;
import org.hanghae99.tddframeworkstudy.userjoin.repository.MemberRepository;
import org.hanghae99.tddframeworkstudy.web.security.util.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 로그인성공() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("testuser");
        member.setPassword("encodedpassword");

        Mockito.when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(member));
        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        Mockito.when(jwtTokenProvider.generateToken(anyString())).thenReturn("dummyToken");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "testuser")
                        .param("password", "password123"))
                .andExpect(status().isOk());
    }

    @Test
    void 로그인실패() throws Exception {
        // Given
        Mockito.when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "testuser")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized());
    }
}
