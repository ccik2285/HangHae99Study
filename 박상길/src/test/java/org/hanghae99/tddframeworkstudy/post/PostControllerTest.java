package org.hanghae99.tddframeworkstudy.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("전체 게시글 목록 조회 API")
    public void selectAllPost() throws Exception {
        // given
        Post post1 = new Post();
        post1.setTitle("test1");
        post1.setAuthor("gil1");
        post1.setContents("작성 내용");
        post1.setCreatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("test2");
        post2.setAuthor("gil2");
        post2.setContents("작성 내용");
        post2.setCreatedAt(LocalDateTime.now().minusDays(1));

        when(postService.getAllPosts()).thenReturn(Arrays.asList(post1, post2));

        // when
        ResultActions perform = mockMvc.perform(get("/getAllPosts"));
        perform
            /*
                then 1
                - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
                - 작성 날짜 기준 내림차순으로 정렬하기
             */
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("test1"))
            .andExpect(jsonPath("$[0].author").value("gil1"))
            .andExpect(jsonPath("$[0].contents").value("작성 내용"))
            .andExpect(jsonPath("$[1].title").value("test2"))
            .andExpect(jsonPath("$[1].author").value("gil2"))
            .andExpect(jsonPath("$[1].contents").value("작성 내용"));

        ObjectMapper objectMapper = new ObjectMapper();

        // then 2
        // return type
        assertThat(objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), Object.class))
            .isInstanceOf(List.class);


    }

}
