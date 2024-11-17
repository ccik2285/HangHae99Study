package org.hanghae99.tddframeworkstudy.userjoin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hanghae99.tddframeworkstudy.userjoin.dto.JoinResponse;
import org.hanghae99.tddframeworkstudy.userjoin.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Slf4j
@Controller
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "회원가입 성공 여부")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    public ResponseEntity<String> registUser(@Valid @RequestBody JoinResponse request) {
        joinService.registerUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("회원가입 성공");
    }

}
