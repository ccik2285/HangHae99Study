package org.hanghae99.tddframeworkstudy.auth.service;

import org.hanghae99.tddframeworkstudy.auth.dto.LoginReq;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;

public interface AuthService {

    // 회원가입
    UserEntity signup(LoginReq loginReq);
}
