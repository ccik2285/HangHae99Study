package org.hanghae99.tddframeworkstudy.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRes {
    Long id;
    Long postId;
    String content;
}
