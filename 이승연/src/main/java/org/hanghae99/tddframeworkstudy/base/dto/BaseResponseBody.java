package org.hanghae99.tddframeworkstudy.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BaseResponseBody {
    private LocalDateTime timestamp;
    private Object content;

    public BaseResponseBody() {
        this.timestamp = LocalDateTime.now();
    }

    public BaseResponseBody(Object content) {
        this.timestamp = LocalDateTime.now();
        this.content = content;
    }
}
