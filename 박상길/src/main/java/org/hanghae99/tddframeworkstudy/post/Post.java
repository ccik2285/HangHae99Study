package org.hanghae99.tddframeworkstudy.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.entity.Base;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private String author;

    Post(PostDto postDto){
        this.title = postDto.getTitle();
        this.contents = postDto.getContents();
        this.author = postDto.getAuthor();
        this.id = postDto.getId();
        this.createdAt = postDto.getCreatedAt();
        this.updatedAt = postDto.getUpdatedAt();
    }

}

