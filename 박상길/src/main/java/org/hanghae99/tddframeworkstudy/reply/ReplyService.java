package org.hanghae99.tddframeworkstudy.reply;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.post.PostRepository;
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
        return null;
    }
}
