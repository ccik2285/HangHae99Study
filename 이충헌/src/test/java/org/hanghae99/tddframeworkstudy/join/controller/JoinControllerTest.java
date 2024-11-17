package org.hanghae99.tddframeworkstudy.join.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hanghae99.tddframeworkstudy.userjoin.controller.JoinController;
import org.hanghae99.tddframeworkstudy.userjoin.entity.Member;
import org.hanghae99.tddframeworkstudy.userjoin.repository.MemberRepository;
import org.hanghae99.tddframeworkstudy.userjoin.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class JoinControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JoinController joincontroller;

    @MockBean
    private JoinService joinservice;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 통합() throws Exception {

        memberRepository.deleteAll();

        Member request = new Member();
        request.setUsername("ccik2285");
        request.setPassword("000000");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(joinservice, times(1)).registerUser("ccik2285", "000000");
    }

    @Test
    void testUsernameExists() {
        // 데이터베이스에 testuser가 있는지 확인
        Member request = new Member();
        request.setUsername("user2344455");
        request.setPassword("Passw0rd!");
        memberRepository.save(request);

        // username이 이미 존재하는 경우
        boolean exists = memberRepository.existsByUsername("user2344455");
        assertTrue(exists);  // 존재하면 true 반환

    }

    @Test
    void testCreatedDate() {
        Member member = new Member();
        member.setUsername("testuser");
        member.setPassword("000000");
        memberRepository.save(member);

        // 저장 후 createdAt과 updatedAt이 자동으로 설정되어 있는지 확인
        assertNotNull(member.getCreatedAt());
        assertNotNull(member.getUpdatedAt());
    }

}
