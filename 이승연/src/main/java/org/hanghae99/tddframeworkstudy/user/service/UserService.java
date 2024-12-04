package org.hanghae99.tddframeworkstudy.user.service;

import java.util.Optional;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;

public interface UserService {

    // 사용자 이름으로 사용자 조회
    Optional<UserEntity> findByUsername(String username);

    // 사용자 저장
    UserEntity save(UserEntity userEntity);
}
