package org.hanghae99.tddframeworkstudy.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BaseResponseBody {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
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
