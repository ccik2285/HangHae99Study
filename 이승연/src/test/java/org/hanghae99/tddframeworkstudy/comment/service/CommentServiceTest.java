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

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

// Service Layer 의 비즈니스 로직을 검증
// 애플리케이션의 전체 컨텍스트를 로드하여 실제 환경에서의 통합 테스트를 수행
// Spring 애플리케이션의 모든 Bean 이 로드되어 실제 Service 와 Repository 들이 사용됨
@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ObjectMapper objectMapper;  // ObjectMapper 를 Mock 처리

    @InjectMocks // 실제 Service 객체 - 주입된 Mock 객체를 사용하여 테스트
    private CommentServiceImpl commentService;

    private CommentReq commentReq;
    private CommentRes commentRes;
    private CommentEntity commentEntity;

    // 상수 정의
    private static final Long POST_ID_1 = 1L;
    private static final Long COMMENT_ID_1 = 1L;
    private static final String COMMENT_CONTENT = "Test Comment";

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

        commentRes = new CommentRes(COMMENT_ID_1, POST_ID_1, COMMENT_CONTENT);  // mock된 응답 객체 초기화

    }

    @Test
    @DisplayName("댓글 작성 Service")
    void saveComment() {

        // Given: 테스트를 위한 준비 작업
        // commentRepository.save()가 호출되면, mock 객체는 comment 객체를 반환하도록 설정
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        when(objectMapper.convertValue(any(CommentEntity.class), eq(CommentRes.class))).thenReturn(commentRes);

        // When: service 의 save 메서드 호출하여 댓글을 저장
        CommentRes commentRes = commentService.save(commentReq);

        // Then: 결과 검증
        // 반환된 CommentRes 객체가 예상대로 댓글 ID, 게시글 ID, 댓글 내용이 일치하는지 확인
        assertNotNull(commentRes); // commentRes null 이 아님을 확인
        assertEquals(commentEntity.getId(), commentRes.getId()); // 댓글 ID 검증
        assertEquals(commentEntity.getPostId(), commentRes.getPostId()); // 게시글 ID 검증
        assertEquals(commentEntity.getContent(), commentRes.getContent()); // 댓글 내용 검증
    }
}
