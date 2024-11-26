package org.hanghae99.tddframeworkstudy.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BaseResponseBody<T> {
    private LocalDateTime timestamp;
    private T content;

    public BaseResponseBody() {
        this.timestamp = LocalDateTime.now();
    }

    public BaseResponseBody(T content) {
        this.timestamp = LocalDateTime.now();
        this.content = content;
    }
}
