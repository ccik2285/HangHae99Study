package org.hanghae99.tddframeworkstudy.user.service;

import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;

public interface UserService {

    // 중복된 사용자 이름이 있는지 확인
    boolean isDuplicateUsername(String username);

    // 사용자 저장
    UserEntity save(UserEntity userEntity);
}
