package org.hanghae99.tddframeworkstudy.reply;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.entity.Base;
import org.hanghae99.tddframeworkstudy.post.Post;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;

    @ManyToOne
    private Post post;

    public Reply(ReplyDto replyDto){
        this.id = replyDto.getId();
        this.contents = replyDto.getContents();
        this.post = replyDto.getPostDto() != null ? new Post(replyDto.getPostDto()) : null;
    }

}
