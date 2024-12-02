package org.hanghae99.tddframeworkstudy.auth;

import org.hanghae99.tddframeworkstudy.common.response.ApiRes;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public ResponseEntity signUp(UserDto userDto){
        return ApiRes.success(null, userDto);
    }
}
