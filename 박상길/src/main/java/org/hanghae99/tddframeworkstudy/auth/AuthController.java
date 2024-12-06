package org.hanghae99.tddframeworkstudy.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.common.response.ApiRes;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    public ResponseEntity<ApiRes<UserDto>> signUp(@RequestBody UserDto userDto) {
        return ApiRes.success("", authService.signUp(userDto));
    }

    @PostMapping("/signIn")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    public ResponseEntity<ApiRes<UserDto>> signIn(@RequestBody UserDto userDto, HttpServletResponse response) {
        return ApiRes.success("로그인 성공", authService.signIn(userDto, response));
    }


}
