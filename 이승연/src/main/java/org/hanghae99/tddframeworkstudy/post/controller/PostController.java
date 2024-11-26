package org.hanghae99.tddframeworkstudy.post.controller;

import org.hanghae99.tddframeworkstudy.base.dto.BaseResponseBody;
import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.hanghae99.tddframeworkstudy.post.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
    public ResponseEntity<BaseResponseBody<PostEntity>> findById(@PathVariable Long id) {

        PostEntity savedPost = postService.findById(id);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(new BaseResponseBody<>(savedPost), headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponseBody<List<PostEntity>>> findAll() {

        List<PostEntity> posts = postService.findAll();

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(new BaseResponseBody<>(posts), headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody<PostEntity>> update(@PathVariable Long id, @RequestBody PostEntity postEntity) {

        PostEntity updatedPost = postService.update(id, postEntity);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(new BaseResponseBody<>(updatedPost), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        postService.delete(id);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(new BaseResponseBody<>(), headers, HttpStatus.NO_CONTENT);
    }
}
