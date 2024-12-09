package org.hanghae99.tddframeworkstudy.reply;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import org.hanghae99.tddframeworkstudy.post.Post;
import org.hanghae99.tddframeworkstudy.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    // reply
    private final Long REPLY_ID = 1L;
    private final Long REPLY_ID2 = 2L;
    private final String REPLY_CONTENTS = "test";
    private final String TEST_USER_NAME = "test";

    // post
    private final Long POST_ID1 = 1L;
    private final Long POST_ID2 = 2L;


    @Test
    @DisplayName("댓글작성 api repository")
    public void write(){
        // given

        // post1
        Post post = new Post();
        post.setId(POST_ID1);

        // post1
        Post post2 = new Post();
        post2.setId(POST_ID2);


        // reply1
        Reply reply = new Reply();
        reply.setId(REPLY_ID);
        reply.setContents(REPLY_CONTENTS);
        reply.setPost(post);

        // reply2
        Reply reply2 = new Reply();
        reply2.setId(REPLY_ID2);
        reply2.setContents(REPLY_CONTENTS);
        reply2.setPost(post2);

        // fail then post entity 없을 시
        assertThatException().isThrownBy(() -> replyRepository.save(reply));
        assertThatException().isThrownBy(() -> replyRepository.save(reply2));

        postRepository.save(post);
        postRepository.save(post2);

        // when
        Reply save1 = replyRepository.save(reply);
        Reply save2 = replyRepository.save(reply2);

        // then
        assertThat(save1.getId()).isEqualTo(reply.getId());
        assertThat(save2.getId()).isEqualTo(reply2.getId());

        assertThat(save1.getPost().getId()).isEqualTo(post.getId());
        assertThat(save2.getPost().getId()).isEqualTo(post2.getId());

    }

}
