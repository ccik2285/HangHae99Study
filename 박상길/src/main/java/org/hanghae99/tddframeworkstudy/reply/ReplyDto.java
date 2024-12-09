package org.hanghae99.tddframeworkstudy.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDto extends BaseDto {

    private Long id;

    private String contents;

    ReplyDto(Reply reply){
        this.id = reply.getId();
        this.contents = reply.getContents();
    }

}
