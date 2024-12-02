package org.hanghae99.tddframeworkstudy.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.hanghae99.tddframeworkstudy.user.User;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.hanghae99.tddframeworkstudy.user.UserRepository;
import org.hanghae99.tddframeworkstudy.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final String USER_NAME = "test";
    private final String USER_PASSWORD = "test1234";

    private final String USER_NAME2 = "test2";

    /**
     * username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성
     * password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
     * @throws Exception
     */
    @Test
    @DisplayName("회원가입 service")
    public void signUp(){
        // given
        UserDto userDto1 = new UserDto();
        userDto1.setName(USER_NAME);
        userDto1.setPassword(USER_PASSWORD);

        UserDto userDto2 = new UserDto();
        userDto2.setName(USER_NAME2);
        userDto2.setPassword(USER_PASSWORD);

        User user1 = new User(userDto1);
        User user2 = new User(userDto2);

        when(userRepository.findByName(user1.getName())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user1);

        when(userRepository.findByName(user2.getName())).thenReturn(Optional.of(user1));

        // when
        UserDto userDto = authService.signUp(userDto1);

        // then
        verify(userRepository, times(1)).findByName(user1.getName());
        verify(passwordEncoder, times(1)).encode(user1.getPassword());

        assertThat(userDto.getName()).isEqualTo(USER_NAME);
        assertThatThrownBy(() -> authService.signUp(userDto2)).isInstanceOf(IllegalArgumentException.class);
    }

}
