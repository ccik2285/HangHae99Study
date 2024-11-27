package org.hanghae99.tddframeworkstudy.auth.service;

import org.hanghae99.tddframeworkstudy.auth.dto.LoginReq;
import org.hanghae99.tddframeworkstudy.auth.dto.LoginRes;
import org.hanghae99.tddframeworkstudy.auth.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.hanghae99.tddframeworkstudy.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원가입
    @Transactional
    @Override
    public UserEntity signup(LoginReq loginReq) {

        // 중복 확인
        if (userService.findByUsername(loginReq.getUsername()).isPresent()) {
            throw new IllegalArgumentException("INVALID USERNAME");
        }

        // 사용자 생성
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginReq.getUsername());
        userEntity.setPassword(passwordEncoder.encode(loginReq.getPassword())); // 비밀번호 암호화

        return userService.save(userEntity);
    }

    // 로그인
    @Override
    public LoginRes login(LoginReq loginReq) {

        // 회원 유무 확인
        UserEntity userEntity = userService.findByUsername(loginReq.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        // 비밀번호 비교
        if (!passwordEncoder.matches(loginReq.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("INVALID PASSWORD");
        }

        // 토큰 발급
        String token = jwtTokenProvider.generateToken(userEntity.getUsername());

        return new LoginRes(token);
    }
}
