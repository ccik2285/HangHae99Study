package org.hanghae99.tddframeworkstudy.reply;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.post.Post;
import org.hanghae99.tddframeworkstudy.post.PostDto;
import org.hanghae99.tddframeworkstudy.post.PostRepository;
import org.hanghae99.tddframeworkstudy.user.User;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.hanghae99.tddframeworkstudy.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private PostRepository postRepository;

    @Spy
    private JwtTokenProvider jwtTokenProvider;

    // reply
    private final Long REPLY_ID = 1L;
    private final Long REPLY_ID2 = 2L;
    private final Long REPLY_ID3 = 3L;
    private final String REPLY_CONTENTS = "test";
    private final String TEST_USER_NAME = "test";
    private final Long TEST_USER_ID = 1L;

    // post
    private final Long POST_ID1 = 1L;
    private final Long POST_ID2 = 2L;


    /**
     * 댓글작성 api 요구사항
     * - 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
     * - 선택한 게시글의 DB 저장 유무를 확인하기
     * - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
     */
    @Test
    @DisplayName("댓글작성 api service")
    public void write(){
        // given
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setName(TEST_USER_NAME);
        user.setRole(UserRole.ADMIN);

        String validToken = jwtTokenProvider.generateToken(user.getName(), user.getId());
        String invalidToken = "invalidToken";

        // post1
        PostDto postDto = new PostDto();
        postDto.setId(POST_ID1);
        Post post = new Post(postDto);

        // post1
        PostDto postDto2 = new PostDto();
        postDto2.setId(POST_ID2);
        Post post2 = new Post(postDto2);


        // reply1
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(REPLY_ID);
        replyDto.setContents(REPLY_CONTENTS);
        replyDto.setPostDto(postDto);
        Reply reply = new Reply(replyDto);

        // reply2
        ReplyDto replyDto2 = new ReplyDto();
        replyDto2.setId(REPLY_ID2);
        replyDto2.setContents(REPLY_CONTENTS);
        replyDto2.setPostDto(postDto2);
        Reply reply2 = new Reply(replyDto2);

        when(replyRepository.save(any())).thenReturn(reply);

        when(postRepository.findById(POST_ID1)).thenReturn(Optional.of(post));
        when(postRepository.findById(POST_ID2)).thenReturn(Optional.empty());

        // when then

        /**
         * 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
         */
        // 토큰 fail jwtTokenProvider
        assertThatThrownBy(() -> jwtTokenProvider.validToken(invalidToken)).isInstanceOf(JwtException.class);
        // 토큰 fail service
        assertThatThrownBy(() -> replyService.write(replyDto, invalidToken)).isInstanceOf(JwtException.class);

        /**
         * 선택한 게시글의 DB 저장 유무를 확인하기
         */
        // fail
        assertThatThrownBy(() -> replyService.write(replyDto2, validToken)).isInstanceOf(EntityNotFoundException.class);

        // when
        ReplyDto saveReply = replyService.write(replyDto, validToken);

        // then
        assertThat(saveReply.getContents()).isEqualTo(REPLY_CONTENTS);

        verify(replyRepository, times(1)).save(any());

    }

    /**
     * 댓글 수정 API 구현 요구사항
     *  토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능
     *  선택한 댓글의 DB 저장 유무를 확인
     *  선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환
     */
    @Test
    @DisplayName("댓글수정 api controller")
    public void updateReply() throws Exception {
        // given
        UserDto userDto = new UserDto();
        userDto.setId(TEST_USER_ID);
        userDto.setName(TEST_USER_NAME);
        userDto.setRole(UserRole.ADMIN);

        String validToken = jwtTokenProvider.generateToken(userDto.getName(), userDto.getId());
        String invalidToken = jwtTokenProvider.generateToken(userDto.getName(), 2L);

        // reply 1 - for succ
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(REPLY_ID);
        replyDto.setContents(REPLY_CONTENTS);
        replyDto.setUserDto(userDto);
        Reply reply = new Reply(replyDto);

        // reply 2 - entity not found
        ReplyDto replyDto2 = new ReplyDto();
        replyDto2.setId(REPLY_ID2);
        replyDto2.setContents(REPLY_CONTENTS);
        Reply reply2 = new Reply(replyDto2);

        // reply 3 - invalid token
        ReplyDto replyDto3 = new ReplyDto();
        replyDto3.setId(REPLY_ID3);
        replyDto3.setContents(REPLY_CONTENTS);
        replyDto3.setUserDto(userDto);
        Reply reply3 = new Reply(replyDto3);


        // stub
        when(replyRepository.findById(REPLY_ID)).thenReturn(Optional.of(reply));
        when(replyRepository.findById(REPLY_ID2)).thenReturn(Optional.empty());
        when(replyRepository.findById(REPLY_ID3)).thenReturn(Optional.of(reply3));

        when(replyRepository.save(any())).thenReturn(reply);

        // when then
        /**
         * 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능
         *
         * 유저와 토큰 불일치
         */
        assertThatThrownBy(() -> replyService.update(replyDto3, invalidToken)).isInstanceOf(JwtException.class);

        /**
         * 선택한 댓글의 DB 저장 유무를 확인
         */
        assertThatThrownBy(() -> replyService.update(replyDto2, validToken)).isInstanceOf(EntityNotFoundException.class);

        /**
         * 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환
         */
        ReplyDto saveReply = replyService.update(replyDto, validToken);

        assertThat(saveReply.getId()).isEqualTo(REPLY_ID);
        verify(replyRepository, times(1)).save(any());
    }

}
