package org.hanghae99.tddframeworkstudy.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("전체 게시글 목록 조회 service")
    public void selectAllPost() throws Exception {

        final String TITLE1 = "test1";
        final String AUTHOR1 = "gil1";
        final String CONTENTS1 = "작성 내용";
        final LocalDateTime LOCAL_DATE_TIME1 = LocalDateTime.of(2024, 11, 25, 0, 0);

        final String TITLE2 = "test2";
        final String AUTHOR2 = "gil2";
        final String CONTENTS2 = "작성 내용2";
        final LocalDateTime LOCAL_DATE_TIME2 = LocalDateTime.of(2024, 11, 24, 0, 0);

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

        when(postRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Arrays.asList(post1, post2));

        // when
        List<Post> allPosts = postService.getAllPosts();

        // then
        // 결과검증
        assertThat(allPosts.size()).isEqualTo(2);

        Post post = allPosts.get(0);
        assertThat(post.getTitle()).isEqualTo(TITLE1);
        assertThat(post.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(post.getContents()).isEqualTo(CONTENTS1);
        assertThat(post.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);


        // then
        // 행위검증
        verify(postRepository, times(1)).findAllByOrderByCreatedAtDesc();

    }

}
