package org.hanghae99.tddframeworkstudy.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;
import org.hanghae99.tddframeworkstudy.post.PostDto;
import org.hanghae99.tddframeworkstudy.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDto extends BaseDto {

    private Long id;

    private String contents;

    private PostDto postDto;

    private UserDto userDto;

    public ReplyDto(Reply reply){
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.postDto = reply.getPost() != null ? new PostDto(reply.getPost()) : null;
        this.userDto = reply.getUser() != null ? new UserDto(reply.getUser()) : null;
    }

}
