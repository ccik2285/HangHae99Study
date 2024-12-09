package org.hanghae99.tddframeworkstudy.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;
import org.hanghae99.tddframeworkstudy.post.PostDto;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDto extends BaseDto {

    private Long id;

    private String contents;

    private PostDto postDto;

    public ReplyDto(Reply reply){
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.postDto = new PostDto(reply.getPost());
    }

}
