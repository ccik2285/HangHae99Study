package org.hanghae99.tddframeworkstudy.user.repository;

import java.util.Optional;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
