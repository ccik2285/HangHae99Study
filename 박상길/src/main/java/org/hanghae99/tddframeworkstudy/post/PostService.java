package org.hanghae99.tddframeworkstudy.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

}
