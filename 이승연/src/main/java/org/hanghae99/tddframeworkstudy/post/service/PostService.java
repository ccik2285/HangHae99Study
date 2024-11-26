package org.hanghae99.tddframeworkstudy.post.service;

import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;

import java.util.List;

public interface PostService {

    PostEntity save(PostEntity postEntity);
    PostEntity findById(Long id);
    List<PostEntity> findAll();
    PostEntity update(Long id, PostEntity postEntity);
    void delete(Long id);
}
