package org.hanghae99.tddframeworkstudy.auth.service;

import org.hanghae99.tddframeworkstudy.auth.dto.LoginReq;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.hanghae99.tddframeworkstudy.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    @Override
    public UserEntity signup(LoginReq loginReq) {

        // 중복 확인
        if (userService.isDuplicateUsername(loginReq.getUsername())) {
            throw new IllegalArgumentException("중복된 사용자 이름입니다.");
        }

        // 사용자 생성
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginReq.getUsername());
        userEntity.setPassword(passwordEncoder.encode(loginReq.getPassword())); // 비밀번호 암호화

        return userService.save(userEntity);
    }
}
