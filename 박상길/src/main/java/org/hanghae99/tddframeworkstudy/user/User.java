package org.hanghae99.tddframeworkstudy.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.entity.Base;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tdd_user")
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(UserDto userDto){
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
    }

}
