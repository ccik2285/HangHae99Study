package org.hanghae99.tddframeworkstudy.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true) // ObjectMapper 가 JSON 으로 변환할 때, 모르는 필드가 있으면 무시하도록 설정
@Getter
@Setter
public class PostRes {

    private Long id;
    private String title;
    private String content;
}
