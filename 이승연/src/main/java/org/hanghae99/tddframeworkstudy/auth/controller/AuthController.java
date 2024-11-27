package org.hanghae99.tddframeworkstudy.auth.controller;

import org.hanghae99.tddframeworkstudy.auth.dto.LoginReq;
import org.hanghae99.tddframeworkstudy.auth.dto.LoginRes;
import org.hanghae99.tddframeworkstudy.auth.service.AuthService;
import org.hanghae99.tddframeworkstudy.base.dto.BaseResponseBody;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseBody<UserEntity>> signup(@RequestBody LoginReq loginReq) {

        UserEntity userEntity = authService.signup(loginReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(userEntity));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<BaseResponseBody<LoginRes>> login(@RequestBody LoginReq loginReq) {

        return ResponseEntity.ok(new BaseResponseBody<>(authService.login(loginReq)));
    }
}
