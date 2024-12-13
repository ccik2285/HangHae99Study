package org.hanghae99.tddframeworkstudy.comment.service;

import org.hanghae99.tddframeworkstudy.comment.dto.CommentReq;
import org.hanghae99.tddframeworkstudy.comment.dto.CommentRes;
import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;
import org.hanghae99.tddframeworkstudy.comment.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Service Layer 의 비즈니스 로직을 검증
// 애플리케이션의 전체 컨텍스트를 로드하여 실제 환경에서의 통합 테스트를 수행
// Spring 애플리케이션의 모든 Bean 이 로드되어 실제 Service 와 Repository 들이 사용됨
@SpringBootTest
@Transactional // 테스트 데이터가 DB에 남지 않도록 롤백 처리
public class CommentServiceIntegrationTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 저장 통합 테스트")
    void saveComment() {
        // Given
        CommentReq commentReq = new CommentReq(1L, "Test Comment");

        // When
        CommentRes commentRes = commentService.save(commentReq);

        // Then
        assertNotNull(commentRes);
        assertEquals("Test Comment", commentRes.getContent());

        // 실제 DB 에서 데이터 확인
        CommentEntity savedEntity = commentRepository.findById(commentRes.getId()).orElse(null);
        assertNotNull(savedEntity);
        assertEquals("Test Comment", savedEntity.getContent());
    }
}
