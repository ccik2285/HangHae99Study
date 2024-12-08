package org.hanghae99.tddframeworkstudy.comment;

import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.base.dto.BaseResponseBody;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponseBody> save(@RequestBody CommentReq commentReq) {

        CommentRes savedComment = commentService.save(commentReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody(savedComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> update(@PathVariable Long id, @RequestBody CommentReq commentReq) {

        CommentRes updatedComment = commentService.update(id, commentReq);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> delete(@PathVariable Long id) {

        commentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
