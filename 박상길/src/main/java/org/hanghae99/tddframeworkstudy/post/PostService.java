package org.hanghae99.tddframeworkstudy.post;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDto> getAllPosts(){

        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        return postList.stream().map(PostDto::new).collect(Collectors.toList());
    }

    public PostDto writePost(PostDto postDto) {
        Post post = new Post(postDto);
        Post save = postRepository.save(post);
        return new PostDto(save);
    }

    public PostDto selectPost(Long id) {
        return null;
    }
}
