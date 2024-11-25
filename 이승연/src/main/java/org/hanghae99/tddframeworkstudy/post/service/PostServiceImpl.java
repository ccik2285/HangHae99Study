package org.hanghae99.tddframeworkstudy.post.service;

import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.hanghae99.tddframeworkstudy.post.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostEntity save(PostEntity postEntity) {

        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity findById(Long id) {

        Optional<PostEntity> newPost = postRepository.findById(id);

        return newPost.orElseThrow(() -> new RuntimeException("NOT FOUND"));
    }

    @Override
    public List<PostEntity> findAll() {

        return postRepository.findAll();
    }

    @Override
    public PostEntity update(Long id, PostEntity postEntity) {

        PostEntity existingPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));

        existingPost.setTitle(postEntity.getTitle());
        existingPost.setContent(postEntity.getContent());

        return postRepository.save(existingPost);
    }

    @Override
    public void delete(Long id) {

        PostEntity existingPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));

        postRepository.delete(existingPost);
    }
}
