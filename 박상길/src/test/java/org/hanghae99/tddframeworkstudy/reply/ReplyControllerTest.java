package org.hanghae99.tddframeworkstudy.reply;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ReplyController.class)
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyService replyService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String REPLY_CONTENTS = "test";

    /**
     * 댓글작성 api 요구사항
     * - 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
     * - 선택한 게시글의 DB 저장 유무를 확인하기
     * - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
     */
    @Test
    @DisplayName("댓글작성 api controller")
    public void writeReply() throws Exception {
        // given
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContents(REPLY_CONTENTS);

        Reply reply = new Reply(replyDto);

        // when
        when(replyService.writeReply(any())).thenReturn(reply);

        // then
        ResultActions resultActions = mockMvc.perform(post("write"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("error"));

        ResultActions resultActions2 = mockMvc.perform(post("write").content(objectMapper.writeValueAsString(reply)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());


        String contents = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        assertThat(contents).isEqualTo(REPLY_CONTENTS);
    }

}
