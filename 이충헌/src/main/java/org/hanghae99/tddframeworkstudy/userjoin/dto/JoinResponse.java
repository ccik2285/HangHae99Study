package org.hanghae99.tddframeworkstudy.userjoin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JoinResponse {

    @NotBlank(message = "아이디를 입력하세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 입니다.")
    private String username;

    @NotBlank(message = "패스워드를 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$",
            message = "패스워드는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 입니다.")
    private String password;
}
