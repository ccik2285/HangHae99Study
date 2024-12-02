package org.hanghae99.tddframeworkstudy.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;

@Getter
@Setter
@NoArgsConstructor
public class PostDto extends BaseDto {

    private Long id;

    private String title;

    private String contents;

    private String author;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

}

