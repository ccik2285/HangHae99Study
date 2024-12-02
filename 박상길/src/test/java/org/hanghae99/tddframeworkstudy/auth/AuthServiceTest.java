package org.hanghae99.tddframeworkstudy.auth;

import static org.mockito.Mockito.*;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @InjectMocks
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
/*
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

        User user2 = new User(userDto2);

        when(userRepository.findByUserName(user1.getName())).thenReturn(null);
        when(userRepository.findByUserName(user2.getName())).thenReturn(user2);

        // when
        authService.signUp()

    }
*/

}
