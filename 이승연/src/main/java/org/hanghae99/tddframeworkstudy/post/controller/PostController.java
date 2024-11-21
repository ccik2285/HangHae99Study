package org.hanghae99.tddframeworkstudy.post.controller;

import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.hanghae99.tddframeworkstudy.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostEntity save(@RequestBody PostEntity postEntity) {
        return postService.save(postEntity);
    }

    @GetMapping("/{id}")
    public PostEntity findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping
    public List<PostEntity> findAll() {
        return postService.findAll();
    }

    @PutMapping("/{id}")
    public PostEntity update(@PathVariable Long id, @RequestBody PostEntity postEntity) {
        return postService.update(id, postEntity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}
