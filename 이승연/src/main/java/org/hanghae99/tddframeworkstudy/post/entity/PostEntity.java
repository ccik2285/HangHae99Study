package org.hanghae99.tddframeworkstudy.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.base.entity.BaseEntity;

@Getter
@Setter
@Table(name = "ss_post")
@Entity
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;
}
