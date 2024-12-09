package org.hanghae99.tddframeworkstudy.reply;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.post.PostRepository;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final PostRepository postRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ReplyDto write (ReplyDto replyDto, String token){
        jwtTokenProvider.validToken(token);

        postRepository.findById(replyDto.getPostDto().getId()).orElseThrow(EntityNotFoundException::new);

        Reply save = replyRepository.save(new Reply(replyDto));

        return new ReplyDto(save);
    }

    public ReplyDto update(ReplyDto replyDto, String token) {
        UserDto userDto = jwtTokenProvider.validToken(token);

        Long userId = userDto.getId();

        Reply reply = replyRepository.findById(replyDto.getId()).orElseThrow(EntityNotFoundException::new);

        if(userId != reply.getUser().getId()){
            throw new JwtException("올바르지 않은 토큰정보");
        }

        reply.setContents(replyDto.getContents());

        Reply save = replyRepository.save(reply);

        return new ReplyDto(save);
    }
}
