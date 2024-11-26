package org.hanghae99.tddframeworkstudy.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    @Operation(summary = "게시글 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
        @ApiResponse(responseCode = "400", description = "게시글 조회 실패")
    })
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/write")
    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
        @ApiResponse(responseCode = "400", description = "게시글 작성 실패")
    })
    public PostDto writePost(@RequestBody PostDto postDto) {
        return postService.writePost(postDto);
    }

}
