package org.hanghae99.tddframeworkstudy.post.controller;

import org.hanghae99.tddframeworkstudy.base.dto.BaseResponseBody;
import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.hanghae99.tddframeworkstudy.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponseBody> save(@RequestBody PostEntity postEntity) {

        PostEntity savedPost =  postService.save(postEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody(savedPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseBody> findById(@PathVariable Long id) {

        PostEntity post = postService.findById(id);

        return ResponseEntity.ok(new BaseResponseBody(post));
    }

    @GetMapping
    public ResponseEntity<BaseResponseBody> findAll() {

        List<PostEntity> posts = postService.findAll();

        return ResponseEntity.ok(new BaseResponseBody(posts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> update(@PathVariable Long id, @RequestBody PostEntity postEntity) {

        PostEntity updatedPost = postService.update(id, postEntity);

        return ResponseEntity.ok(new BaseResponseBody(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
