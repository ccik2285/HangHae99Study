package org.hanghae99.tddframeworkstudy.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;
import org.hanghae99.tddframeworkstudy.comment.repository.CommentRepository;

// JUnit 5 의 테스트 어노테이션
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

// Mock 을 활용한 Service Layer 단위 테스트
// 실제 애플리케이션 컨텍스트를 로드하지 않고, Service Layer 의 비즈니스 로직을 독립적으로 검증
// Repository 및 ObjectMapper 의존성은 Mock 으로 대체하여 동작을 시뮬레이션
@ExtendWith(MockitoExtension.class) // Mockito 테스트 설정
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ObjectMapper objectMapper;  // ObjectMapper 를 Mock 처리

    @InjectMocks // 실제 Service 객체 - 주입된 Mock 객체를 사용하여 테스트
    private CommentServiceImpl commentService;

    private CommentReq commentReq;
    private CommentRes commentRes;
    private CommentRes updateCommentRes;
    private CommentEntity commentEntity;

    // 상수 정의
    private static final Long POST_ID_1 = 1L;
    private static final Long COMMENT_ID_1 = 1L;
    private static final String COMMENT_CONTENT = "Test Comment";
    private static final String UPDATED_COMMENT_CONTENT = "Updated Comment";

    @BeforeEach // 각 테스트 메서드 실행 전에 호출되어 초기화 작업을 수행
    void setUp() {

        // 댓글 요청 데이터 (클라이언트가 보낼 데이터)
        commentReq = new CommentReq(
                POST_ID_1, // 게시글 ID
                COMMENT_CONTENT // 댓글 내용
        );

        // 댓글 도메인 객체 (서비스에서 사용할 실제 객체)
        commentEntity = new CommentEntity(
                COMMENT_ID_1, // 댓글 ID
                POST_ID_1, // 게시글 ID
                COMMENT_CONTENT // 댓글 내용
        );

        // mock 처리 된 응답 객체 초기화
        commentRes = new CommentRes(
                COMMENT_ID_1, // 댓글 ID
                POST_ID_1, // 게시글 ID
                COMMENT_CONTENT // 댓글 내용
        );

        updateCommentRes = new CommentRes(
                COMMENT_ID_1, // 댓글 ID
                POST_ID_1, // 게시글 ID
                UPDATED_COMMENT_CONTENT // 수정된 댓글 내용
        );

    }

    @Test
    @DisplayName("댓글 작성 Service")
    void saveComment() {

        // Given: 테스트를 위한 준비 작업
        // commentRepository.save()가 호출되면, mock 객체는 commentEntity 객체를 반환하도록 설정
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        // objectMapper.convertValue()가 호출되면, mock 객체는 commentRes 객체를 반환하도록 설정
        when(objectMapper.convertValue(any(CommentEntity.class), eq(CommentRes.class))).thenReturn(commentRes);

        // When: service 의 save 메서드 호출하여 댓글을 저장
        CommentRes commentRes = commentService.save(commentReq);

        // Then: 결과 검증
        // 반환된 CommentRes 객체가 예상대로 댓글 ID, 게시글 ID, 댓글 내용이 일치하는지 확인
        assertNotNull(commentRes); // commentRes null 이 아님을 확인
        assertEquals(COMMENT_ID_1, commentRes.getId()); // 댓글 ID 검증
        assertEquals(POST_ID_1, commentRes.getPostId()); // 게시글 ID 검증
        assertEquals(COMMENT_CONTENT, commentRes.getContent()); // 댓글 내용 검증
    }

    @Test
    @DisplayName("댓글 수정 Service")
    void updateComment() {

        // Given: 테스트를 위한 준비 작업
        // 댓글 수정 요청 데이터와 기대하는 수정된 댓글 내용
        CommentReq updateReq = new CommentReq(COMMENT_ID_1, UPDATED_COMMENT_CONTENT);

        // commentRepository.findById()가 호출되면 mock 객체는 commentEntity 객체를 반환
        when(commentRepository.findById(COMMENT_ID_1)).thenReturn(java.util.Optional.of(commentEntity));

        // commentRepository.save()가 호출되어 댓글 저장 시, 수정된 댓글이 반환되도록 설정
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(new CommentEntity(COMMENT_ID_1, POST_ID_1, UPDATED_COMMENT_CONTENT));

        // objectMapper.convertValue()가 호출되면, mock 객체는 updateCommentRes 객체를 반환하도록 설정
        when(objectMapper.convertValue(any(CommentEntity.class), eq(CommentRes.class))).thenReturn(updateCommentRes);

        // When: service 의 update 메서드를 호출하여 댓글을 수정
        CommentRes commentRes = commentService.update(1L, updateReq);

        // Then: 수정된 내용이 예상대로 반영되었는지 확인
        assertNotNull(commentRes); // commentRes 가 null 이 아님을 확인
        assertEquals(COMMENT_ID_1, commentRes.getId()); // 수정된 댓글의 ID가 일치하는지 검증
        assertEquals(UPDATED_COMMENT_CONTENT, commentRes.getContent()); // 수정된 댓글 내용이 일치하는지 검증
    }

    @Test
    @DisplayName("댓글 삭제 Service")
    void deleteComment() {

        // Given: 댓글 삭제를 위한 초기 설정
        // commentRepository.existsById()가 호출되면 true 를 반환하도록 설정 (삭제할 댓글이 존재한다고 가정)
        when(commentRepository.findById(COMMENT_ID_1)).thenReturn(Optional.of(commentEntity));

        // When: service 의 delete 메서드 호출하여 댓글을 삭제
        commentService.delete(COMMENT_ID_1);

        // Then: commentRepository.deleteById 가 호출되었는지 검증
        verify(commentRepository, times(1)).delete(commentEntity);
    }
}
