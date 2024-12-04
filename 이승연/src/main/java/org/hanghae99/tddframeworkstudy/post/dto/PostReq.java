package org.hanghae99.tddframeworkstudy.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.post.entity.PostEntity;

@Getter
@Setter
public class PostReq {

    private Long id;
    private String title;
    private String content;

    public PostEntity toEntity() {
        return PostEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
