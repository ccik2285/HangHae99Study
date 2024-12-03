package org.hanghae99.tddframeworkstudy.auth;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
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

    private final JwtTokenProvider jwtTokenProvider;

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

    public UserDto signIn(UserDto userDto, HttpServletResponse response) {
        Optional<User> findUser = userRepository.findByName(userDto.getName());
        if(!findUser.isPresent()){
            throw new EntityNotFoundException("회원정보가 존재하지 않습니다.");
        }

        User user = findUser.get();

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getName());

        response.setHeader("Authorization", "Bearer " + token);

        return new UserDto(user);
    }
}
