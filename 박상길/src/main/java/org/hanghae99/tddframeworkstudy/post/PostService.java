package org.hanghae99.tddframeworkstudy.post;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDto> getAllPosts(){

        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return postList.stream().map(PostDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PostDto writePost(PostDto postDto) {
        Post post = new Post(postDto);
        Post save = postRepository.save(post);
        return new PostDto(save);
    }

    public PostDto selectPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("게시글을 찾을 수 없습니다. id - %s", id))
        );
        return new PostDto(post);
    }

    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("게시글을 찾을 수 없습니다. id - %s", id))
        );

        if(!post.getPassword().equals(postDto.getPassword())) {
            throw new IllegalArgumentException(String.format("패스워드가 일치하지 않습니다. id - %s", id));
        }

        post.setTitle(postDto.getTitle());
        post.setContents(postDto.getContents());

        postRepository.save(post);

        return new PostDto(post);
    }

    @Transactional
    public void deletePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("게시글을 찾을 수 없습니다. id - %s", id))
        );

        if(!post.getPassword().equals(postDto.getPassword())) {
            throw new IllegalArgumentException(String.format("패스워드가 일치하지 않습니다. id - %s", id));
        }

        postRepository.delete(post);
    }
}
