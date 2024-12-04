package org.hanghae99.tddframeworkstudy.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hanghae99.tddframeworkstudy.post.dto.PostReq;
import org.hanghae99.tddframeworkstudy.post.dto.PostRes;
import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.hanghae99.tddframeworkstudy.post.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public PostServiceImpl(PostRepository postRepository, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PostRes save(PostReq PostReq) {

        PostEntity postEntity = PostReq.toEntity();
        PostEntity newPost = postRepository.save(postEntity);

        return objectMapper.convertValue(newPost, PostRes.class);
    }

    @Override
    public PostRes findById(Long id) {

        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        PostEntity postEntity = optionalPostEntity.orElseThrow(() -> new EntityNotFoundException(id.toString()));

        return objectMapper.convertValue(postEntity, PostRes.class);
    }

    @Override
    public List<PostRes> findAll() {

        List<PostEntity> postEntityList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return postEntityList.stream().map(postEntity -> objectMapper.convertValue(postEntity, PostRes.class)).toList();
    }

    @Transactional
    @Override
    public PostRes update(Long id, PostReq postReq) {

        PostEntity existingPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        if (postReq.getTitle() != null) {
            existingPost.setTitle(postReq.getTitle());
        }
        if (postReq.getContent() != null) {
            existingPost.setContent(postReq.getContent());
        }

        PostEntity updatedPost = postRepository.save(existingPost);

        return objectMapper.convertValue(updatedPost, PostRes.class);
    }

    @Override
    public void delete(Long id) {

        PostEntity existingPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        postRepository.delete(existingPost);
    }
}
