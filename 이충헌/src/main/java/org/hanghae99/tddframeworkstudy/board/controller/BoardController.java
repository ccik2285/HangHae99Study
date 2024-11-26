package org.hanghae99.tddframeworkstudy.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hanghae99.tddframeworkstudy.board.dto.BoardResponse;
import org.hanghae99.tddframeworkstudy.board.entity.Board;
import org.hanghae99.tddframeworkstudy.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @Operation(summary = "모든 게시글 조회 API", description = "모든 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllPosts());
    }

    @PostMapping
    @Operation(summary = "게시글 작성 API", description = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 작성 실패")
    })
    public Board createBoard(@Valid @RequestBody BoardResponse request) {
        return boardService.createBoard(boardService.convertToBoardEntity(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "게시글 상세 조회 API", description = "게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 상세 조회 실패")
    })
    public ResponseEntity<Board> getBoardById(@PathVariable(value = "id") Long id) {
        return boardService.getBoardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 업데이트 API", description = "게시글 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 업데이트 실패")
    })
    public ResponseEntity<Board> updateBoard(@PathVariable(value = "id") Long id,
                                           @RequestBody Board updatedBoard,
                                           @RequestParam(value = "password") String password) {
        try {
            return ResponseEntity.ok(boardService.updateBoard(id, updatedBoard, password));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제 API", description = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 삭제 실패")
    })
    public ResponseEntity<Void> deleteBoard(@PathVariable(value = "id") Long id,
                                           @RequestParam(value = "password") String password) {
        try {
            boardService.deleteBoard(id, password);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
