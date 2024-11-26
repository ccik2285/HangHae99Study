package org.hanghae99.tddframeworkstudy.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    private final Long ID1 = 1L;
    private final String TITLE1 = "test1";
    private final String AUTHOR1 = "gil1";
    private final String CONTENTS1 = "작성 내용";
    private final LocalDateTime LOCAL_DATE_TIME1 = LocalDateTime.of(2024, 11, 25, 0, 0);

    private final Long ID2 = 2L;
    private final String TITLE2 = "test2";
    private final String AUTHOR2 = "gil2";
    private final String CONTENTS2 = "작성 내용2";
    private final LocalDateTime LOCAL_DATE_TIME2 = LocalDateTime.of(2024, 11, 24, 0, 0);


    @Test
    @DisplayName("전체 게시글 목록 조회 service")
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

        when(postRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Arrays.asList(post1, post2));

        // when
        List<PostDto> allPosts = postService.getAllPosts();

        // then
        // 결과검증
        assertThat(allPosts.size()).isEqualTo(2);

        PostDto post = allPosts.get(0);
        assertThat(post.getTitle()).isEqualTo(TITLE1);
        assertThat(post.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(post.getContents()).isEqualTo(CONTENTS1);
        assertThat(post.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);


        // then
        // 행위검증
        verify(postRepository, times(1)).findAllByOrderByCreatedAtDesc();

    }

    @Test
    @DisplayName("게시글 작성 service")
    public void writePost() throws Exception {

        // given
        PostDto postDto = new PostDto();
        postDto.setTitle(TITLE1);
        postDto.setAuthor(AUTHOR1);
        postDto.setContents(CONTENTS1);
        postDto.setCreatedAt(LOCAL_DATE_TIME1);

        Post postEntity = new Post(postDto);

        when(postRepository.save(any())).thenReturn(postEntity);

        // when
        PostDto savePost = postService.writePost(postDto);

        // then
        // 결과검증
        assertThat(savePost.getTitle()).isEqualTo(TITLE1);
        assertThat(savePost.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(savePost.getContents()).isEqualTo(CONTENTS1);
        assertThat(savePost.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);

        // then
        // 행위검증
        verify(postRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("선택 게시글 조회 service")
    public void selectPost() throws Exception {

        // given
        PostDto postDto = new PostDto();
        postDto.setTitle(TITLE1);
        postDto.setAuthor(AUTHOR1);
        postDto.setContents(CONTENTS1);
        postDto.setCreatedAt(LOCAL_DATE_TIME1);

        Post postEntity = new Post(postDto);

        when(postRepository.findById(ID1)).thenReturn(Optional.of(postEntity));
        when(postRepository.findById(ID2)).thenThrow(EntityNotFoundException.class);

        // when
        PostDto findPost = postService.selectPost(ID1);

        // then
        // 결과검증
        assertThat(findPost.getTitle()).isEqualTo(TITLE1);
        assertThat(findPost.getAuthor()).isEqualTo(AUTHOR1);
        assertThat(findPost.getContents()).isEqualTo(CONTENTS1);
        assertThat(findPost.getCreatedAt()).isEqualTo(LOCAL_DATE_TIME1);

        // then
        // 행위검증
        verify(postRepository, times(1)).findById(ID1);
        assertThatThrownBy(() -> postService.selectPost(ID2)).isInstanceOf(EntityNotFoundException.class);

    }



}
