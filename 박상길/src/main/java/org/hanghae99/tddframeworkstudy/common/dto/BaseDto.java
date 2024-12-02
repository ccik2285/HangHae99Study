package org.hanghae99.tddframeworkstudy.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class BaseDto {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    protected LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    protected LocalDateTime updatedAt;

}
