package org.hanghae99.tddframeworkstudy.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.comment.entity.CommentEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReq {
    private Long postId;
    private String content;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .postId(postId)
                .content(content)
                .build();
    }
}
