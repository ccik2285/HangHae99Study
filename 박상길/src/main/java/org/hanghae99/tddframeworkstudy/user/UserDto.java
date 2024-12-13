package org.hanghae99.tddframeworkstudy.user;

import java.util.regex.Pattern;
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

    private UserRole role;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.role = user.getRole();
    }

    public boolean validName(){
        String regex = "^[a-z0-9]{4,10}$";
        return Pattern.matches(regex, this.name);
    }

    public boolean validPassword(){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
        return Pattern.matches(regex, this.password);
    }



}
