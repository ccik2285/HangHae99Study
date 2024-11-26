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

        return postList.stream().map(
            post -> {
                PostDto postDto = new PostDto();
                postDto.setId(post.getId());
                postDto.setTitle(post.getTitle());
                postDto.setAuthor(post.getAuthor());
                postDto.setContents(post.getContents());
                postDto.setCreatedAt(post.getCreatedAt());
                return postDto;
            }
        ).collect(Collectors.toList());
    }

}
