package org.hanghae99.tddframeworkstudy.post.service;

import org.hanghae99.tddframeworkstudy.post.dto.PostReq;
import org.hanghae99.tddframeworkstudy.post.dto.PostRes;

import java.util.List;

public interface PostService {

    PostRes save(PostReq postReq);
    PostRes findById(Long id);
    List<PostRes> findAll();
    PostRes update(Long id, PostReq postReq);
    void delete(Long id);
}
