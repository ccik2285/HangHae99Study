package org.hanghae99.tddframeworkstudy.comment.service;

import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public CommentRes save(CommentReq commentReq) {
        // TODO CommentController 테스트 진행중
        return null;
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
