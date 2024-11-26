package org.hanghae99.tddframeworkstudy.post;

import lombok.Getter;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;

@Getter
@Setter
public class PostDto extends BaseDto {

    private Long id;

    private String title;

    private String contents;

    private String author;

}

