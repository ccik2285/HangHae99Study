package org.hanghae99.tddframeworkstudy.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {

    private String username;

    private String password;
}
