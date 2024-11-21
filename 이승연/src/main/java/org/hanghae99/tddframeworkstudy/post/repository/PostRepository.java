package org.hanghae99.tddframeworkstudy.post.repository;

import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
