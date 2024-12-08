package org.hanghae99.tddframeworkstudy.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;
import org.hanghae99.tddframeworkstudy.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CommentRes save(CommentReq commentReq) {

        CommentEntity commentEntity = commentReq.toEntity();
        CommentEntity newComment = commentRepository.save(commentEntity);

        return objectMapper.convertValue(newComment, CommentRes.class);
    }

    @Override
    public CommentRes update(Long id, CommentReq commentReq) {
        // TODO CommentController 테스트 진행중
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO CommentController 테스트 진행중
    }
}
