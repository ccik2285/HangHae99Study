package org.hanghae99.tddframeworkstudy.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hanghae99.tddframeworkstudy.comment.CommentController;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Mockito 및 Mock 설정
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

// JSON 검증
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Controller 관련 테스트를 수행
// Spring MVC 테스트 환경을 설정하여 HTTP 요청을 보내고 응답을 받는 부분만을 집중적으로 테스트
// Controller 와 관련된 Bean 들만 로드되며, Service 같은 다른 레이어들은 Mock 객체로 주의
@WebMvcTest(CommentController.class) // CommentController 만 테스트
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean 으로 주입된 Mock 객체로 실제 구현된 로직을 실행하지 않고, when 으로 정의한 값만 반환
    @MockBean
    private CommentService commentService;

    // 공통된 JSON 경로
    private static final String BASE_JSON_PATH = "$.content";

    // 상수 정의
    private static final Long POST_ID_1 = 1L;
    private static final Long COMMENT_ID_1 = 1L;
    private static final String COMMENT_CONTENT = "Test Comment";
    private static final String API_URL = "/api/comment";

    @Test
    @DisplayName("댓글 작성 Controller")
    void saveComment() throws Exception {

        // Given: 테스트를 위한 준비 작업
        // 요청 데이터 (클라이언트가 보낼 데이터)
        CommentReq request = new CommentReq(
                POST_ID_1, // 게시글 ID
                COMMENT_CONTENT // 댓글 내용
        );

        // 응답 데이터 (서비스에서 반환할 데이터)
        CommentRes response = new CommentRes(
                COMMENT_ID_1, // 댓글 ID
                POST_ID_1, // 게시글 ID
                COMMENT_CONTENT // 댓글 내용
        );

        // commentService 의 save 메서드가 호출될 때 위 response 를 반환하도록 설정
        when(commentService.save(any(CommentReq.class))).thenReturn(response);

        // When & Then: 테스트 실행 및 검증
        mockMvc.perform(post(API_URL) // HTTP POST 요청
                        .contentType(MediaType.APPLICATION_JSON) // 요청 본문은 JSON 형식
                        .content(new ObjectMapper().writeValueAsString(request))) // 요청 데이터를 JSON 으로 직렬화하여 본문에 추가
                .andExpect(status().isCreated()) // HTTP 상태 코드 201 Created 확인
                .andExpect(jsonPath(BASE_JSON_PATH + ".id").value(COMMENT_ID_1)) // 응답 JSON 의 id 필드 값 검증
                .andExpect(jsonPath(BASE_JSON_PATH + ".postId").value(POST_ID_1)) // 응답 JSON 의 postId 필드 값 검증
                .andExpect(jsonPath(BASE_JSON_PATH + ".content").value(COMMENT_CONTENT)); // 응답 JSON 의 content 필드 값 검증
    }

    @Test
    @DisplayName("댓글 수정 Controller")
    void updateComment() throws Exception {

        final String UPDATED_COMMENT_CONTENT = "Updated Comment";

        // Given: 테스트를 위한 준비 작업
        // 요청 데이터 (클라이언트가 보낼 데이터)
        CommentReq request = new CommentReq(
                POST_ID_1, // 게시글 ID
                UPDATED_COMMENT_CONTENT // 댓글 내용
        );

        // 응답 데이터 (서비스에서 반환할 데이터)
        CommentRes response = new CommentRes(
                COMMENT_ID_1, // 댓글 ID
                POST_ID_1, // 게시글 ID
                UPDATED_COMMENT_CONTENT // 댓글 내용
        );

        // commentService 의 update 메서드가 호출될 때 위 response 를 반환하도록 설정
        when(commentService.update(eq(COMMENT_ID_1), any(CommentReq.class))).thenReturn(response);

        // When & Then: 테스트 실행 및 검증
        mockMvc.perform(put(API_URL + "/{id}", COMMENT_ID_1) // HTTP PUT 요청
                        .contentType(MediaType.APPLICATION_JSON) // 요청 본문은 JSON 형식
                        .content(new ObjectMapper().writeValueAsString(request))) // 요청 데이터를 JSON 으로 직렬화하여 본문에 추가
                .andExpect(status().isOk()) // HTTP 상태 코드 200 OK 확인
                .andExpect(jsonPath(BASE_JSON_PATH + ".id").value(COMMENT_ID_1)) // 응답 JSON 의 id 필드 값 검증
                .andExpect(jsonPath(BASE_JSON_PATH + ".content").value(UPDATED_COMMENT_CONTENT)); // 응답 JSON 의 content 필드 값 검증

    }

    @Test
    @DisplayName("댓글 삭제 Controller")
    void deleteComment() throws Exception {

        // Given: 테스트를 위한 준비 작업
        doNothing().when(commentService).delete(COMMENT_ID_1);

        // When & Then: 테스트 실행 및 검증
        mockMvc.perform(delete(API_URL + "/{id}", COMMENT_ID_1)) // HTTP DELETE 요청
                .andExpect(status().isNoContent()); // HTTP 상태 코드 204 No Content 확인

    }
}
