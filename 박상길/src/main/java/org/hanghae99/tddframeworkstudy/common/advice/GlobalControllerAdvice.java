package org.hanghae99.tddframeworkstudy.common.advice;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hanghae99.tddframeworkstudy.common.response.ApiRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiRes> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiRes.error("관리자에게 문의 바랍니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiRes> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ApiRes.error(e.getMessage());
    }

/*
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiRes> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ApiRes.error(e.getMessage());
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<ApiRes> handleJwtException(JwtException e) {
        log.error(e.getMessage(), e);
        return ApiRes.error(e.getMessage());
    }
*/


}
