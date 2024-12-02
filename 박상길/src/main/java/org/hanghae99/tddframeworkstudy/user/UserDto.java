package org.hanghae99.tddframeworkstudy.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hanghae99.tddframeworkstudy.common.dto.BaseDto;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {

    private Long id;

    private String name;

    private String password;

    UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

}
