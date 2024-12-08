package org.hanghae99.tddframeworkstudy.comment.service;

import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;

public interface CommentService {

    CommentRes save(CommentReq commentReq);

    CommentRes update(Long id, CommentReq commentReq);

    void delete(Long id);
}
