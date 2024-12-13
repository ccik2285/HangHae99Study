package org.hanghae99.tddframeworkstudy.post.controller;

import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.base.dto.BaseResponseBody;
import org.hanghae99.tddframeworkstudy.post.dto.PostReq;
import org.hanghae99.tddframeworkstudy.post.dto.PostRes;
import org.hanghae99.tddframeworkstudy.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponseBody> save(@RequestBody PostReq postReq) {

        PostRes savedPost =  postService.save(postReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody(savedPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseBody> findById(@PathVariable Long id) {

        PostRes post = postService.findById(id);

        return ResponseEntity.ok(new BaseResponseBody(post));
    }

    @GetMapping
    public ResponseEntity<BaseResponseBody> findAll() {

        List<PostRes> posts = postService.findAll();

        return ResponseEntity.ok(new BaseResponseBody(posts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> update(@PathVariable Long id, @RequestBody PostReq postReq) {

        PostRes updatedPost = postService.update(id, postReq);

        return ResponseEntity.ok(new BaseResponseBody(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> delete(@PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
