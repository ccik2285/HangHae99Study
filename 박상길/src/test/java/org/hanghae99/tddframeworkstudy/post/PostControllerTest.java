package org.hanghae99.tddframeworkstudy.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String TITLE1 = "test1";
    private final String AUTHOR1 = "gil1";
    private final String CONTENTS1 = "작성 내용";
    private final LocalDateTime LOCAL_DATE_TIME1 = LocalDateTime.of(2024, 11, 25, 0, 0);

    private final String TITLE2 = "test2";
    private final String AUTHOR2 = "gil2";
    private final String CONTENTS2 = "작성 내용2";
    private final LocalDateTime LOCAL_DATE_TIME2 = LocalDateTime.of(2024, 11, 24, 0, 0);

    @Test
    @DisplayName("전체 게시글 목록 조회 API")
    public void selectAllPost() throws Exception {


        // given
        Post post1 = new Post();
        post1.setTitle(TITLE1);
        post1.setAuthor(AUTHOR1);
        post1.setContents(CONTENTS1);
        post1.setCreatedAt(LOCAL_DATE_TIME1);

        Post post2 = new Post();
        post2.setTitle(TITLE2);
        post2.setAuthor(AUTHOR2);
        post2.setContents(CONTENTS2);
        post2.setCreatedAt(LOCAL_DATE_TIME2);

        when(postService.getAllPosts()).thenReturn(Arrays.asList(post1, post2));

        // when
        ResultActions perform = mockMvc.perform(get("/posts"));
        perform
            /*
                then 1
                - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
                - 작성 날짜 기준 내림차순으로 정렬하기
             */
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value(TITLE1))
            .andExpect(jsonPath("$[0].author").value(AUTHOR1))
            .andExpect(jsonPath("$[0].contents").value(CONTENTS1))

            .andExpect(jsonPath("$[1].title").value(TITLE2))
            .andExpect(jsonPath("$[1].author").value(AUTHOR2))
            .andExpect(jsonPath("$[1].contents").value(CONTENTS2));



        // then 2
        // return type
        byte[] contentAsString = perform.andReturn().getResponse().getContentAsByteArray();
        assertThat(objectMapper.readValue(new String(contentAsString, "UTF-8"), Object.class))
            .isInstanceOf(List.class);


        // then 3
        List<Post> o = objectMapper.readValue(new String(contentAsString, "UTF-8"), new TypeReference<>() {});

        Post post = o.get(0);
        assertThat(post.getTitle()).isEqualTo(TITLE1);
        assertThat(post.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(post.getContents()).isEqualTo(CONTENTS1);
        assertThat(post.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);

        // then 4
        assertThat(post.getTitle()).isNotEqualTo(TITLE2);
        assertThat(post.getAuthor()).isNotEqualTo(AUTHOR2);
        assertThat(post.getContents()).isNotEqualTo(CONTENTS2);
        assertThat(post.getCreatedAt()).isNotEqualTo(LOCAL_DATE_TIME2);

    }

    @Test
    @DisplayName("게시글 작성 api")
    public void writePost() throws Exception {
        // given
        Post post1 = new Post();
        post1.setTitle(TITLE1);
        post1.setAuthor(AUTHOR1);
        post1.setContents(CONTENTS1);
        post1.setCreatedAt(LOCAL_DATE_TIME1);

        when(postService.writePost(post1)).thenReturn(post1);


        // when
        ResultActions perform = mockMvc.perform(post("/write").content(objectMapper.writeValueAsString(post1)))
            .andExpect(status().isOk());

        // then
        byte[] contentAsString = perform.andReturn().getResponse().getContentAsByteArray();
        Post post = objectMapper.readValue(new String(contentAsString, "UTF-8"), Post.class);

        assertThat(post.getTitle()).isEqualTo(TITLE1);
        assertThat(post.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(post.getContents()).isEqualTo(CONTENTS1);
        assertThat(post.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);

    }

}
