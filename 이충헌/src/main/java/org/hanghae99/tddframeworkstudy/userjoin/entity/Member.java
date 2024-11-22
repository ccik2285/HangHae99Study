package org.hanghae99.tddframeworkstudy.userjoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.entity.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
