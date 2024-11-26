package org.hanghae99.tddframeworkstudy.post;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

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

        postRepository.saveAll(Arrays.asList(post1, post2));

        // when
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        // then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getTitle()).isEqualTo(TITLE1);
        assertThat(posts.get(1).getTitle()).isEqualTo(TITLE2);

    }

    @Test
    @DisplayName("게시글 작성 data")
    public void writePost() throws Exception {
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

        // when
        Post save = postRepository.save(post1);

        // then
        // 동치성 검증
        assertThat(save).isEqualTo(post1);
        assertThat(save).isNotEqualTo(post2);

    }

}
