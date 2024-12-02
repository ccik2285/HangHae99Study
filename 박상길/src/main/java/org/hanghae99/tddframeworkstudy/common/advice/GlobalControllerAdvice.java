package org.hanghae99.tddframeworkstudy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.hanghae99.tddframeworkstudy.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error("관리자에게 문의 바랍니다.");
    }

}
