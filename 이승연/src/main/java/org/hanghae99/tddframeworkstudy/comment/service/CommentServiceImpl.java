package org.hanghae99.tddframeworkstudy.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;
import org.hanghae99.tddframeworkstudy.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public CommentRes update(Long id, CommentReq commentReq) {

        CommentEntity existingComment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        if (commentReq.getContent() != null) {
            existingComment.setContent(commentReq.getContent());
        }

        CommentEntity updatedComment = commentRepository.save(existingComment);

        return objectMapper.convertValue(updatedComment, CommentRes.class);
    }

    @Override
    public void delete(Long id) {

        CommentEntity existingComment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        commentRepository.delete(existingComment);
    }
}
