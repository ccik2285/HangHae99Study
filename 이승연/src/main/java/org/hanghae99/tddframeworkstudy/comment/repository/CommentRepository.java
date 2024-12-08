package org.hanghae99.tddframeworkstudy.comment.repository;

import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
