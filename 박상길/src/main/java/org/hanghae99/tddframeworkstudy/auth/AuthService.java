package org.hanghae99.tddframeworkstudy.auth;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.user.User;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.hanghae99.tddframeworkstudy.user.UserRepository;
import org.hanghae99.tddframeworkstudy.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto signUp(UserDto userDto){
        Optional<User> findUser = userRepository.findByName(userDto.getName());
        if(findUser.isPresent()){
            throw new IllegalArgumentException("중복 name");
        }

        if (!userDto.validName()){
            throw new IllegalArgumentException("이름 형식 오류");
        }

        if (!userDto.validPassword()){
            throw new IllegalArgumentException("비밀번호 형식 오류");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return new UserDto(userRepository.save(user));
    }
}
