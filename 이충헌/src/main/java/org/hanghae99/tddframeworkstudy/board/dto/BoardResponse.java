package org.hanghae99.tddframeworkstudy.board.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponse {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "작성자는 필수 입력값입니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

}
