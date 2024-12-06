package org.hanghae99.tddframeworkstudy.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.hanghae99.tddframeworkstudy.user.UserRole;
import org.hanghae99.tddframeworkstudy.user.User;
import org.hanghae99.tddframeworkstudy.user.UserDto;
import org.hanghae99.tddframeworkstudy.user.UserRepository;
import org.hanghae99.tddframeworkstudy.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private HttpServletResponse response;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private final String USER_NAME = "test";
    private final String USER_NAME2 = "test2";
    private final String USER_NAME3 = "test3";

    private final String LEGACY_USER_PASSWORD = "test1234";
    private final String VALID_USER_PASSWORD = "test123!A";

    /**
     * username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성
     * password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
     *
     * 수정 내용 issue #20
     *  - password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(az, AZ), 숫자(0~9), 특수문자로 구성되어야 한다.
     *  - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
     * @throws Exception
     *
     * ## 테스트 내용
     * - password 검증
     * - 회원 존재유무 검증
     */
    @Test
    @DisplayName("회원가입 service1")
    public void signUp1(){
        // given
        // test case1 올바르지 않은 비밀번호 형식
        UserDto userDto1 = new UserDto();
        userDto1.setName(USER_NAME);
        userDto1.setPassword(LEGACY_USER_PASSWORD);

        // test case2 이미 존재하는 유저
        UserDto userDto2 = new UserDto();
        userDto2.setName(USER_NAME2);
        userDto2.setPassword(LEGACY_USER_PASSWORD);

        // test case3 올바른 비밀번호 형식
        UserDto userDto3 = new UserDto();
        userDto3.setName(USER_NAME3);
        userDto3.setPassword(VALID_USER_PASSWORD);

        User user1 = new User(userDto1);
        User user2 = new User(userDto2);
        User user3 = new User(userDto3);

        // stub
        when(userRepository.findByName(user1.getName())).thenReturn(Optional.empty());
        when(userRepository.findByName(user2.getName())).thenReturn(Optional.of(user2));
        when(userRepository.findByName(user3.getName())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user3);


        /**
         * test case 1
         */

        // when then
        assertThatThrownBy(() -> authService.signUp(userDto1)).isInstanceOf(IllegalArgumentException.class);
        verify(userRepository, times(1)).findByName(userDto1.getName());

        /**
         * test case 2
         */

        // when then
        assertThatThrownBy(() -> authService.signUp(userDto2)).isInstanceOf(IllegalArgumentException.class);
        verify(userRepository, times(1)).findByName(userDto2.getName());

        /**
         * test case 3
         */

        // when
        UserDto tc_userDto = authService.signUp(userDto3);

        // then
        verify(userRepository, times(1)).findByName(userDto3.getName());
        verify(passwordEncoder, times(1)).encode(userDto3.getPassword());
        verify(userRepository, times(1)).save(any());

        assertThat(tc_userDto.getName()).isEqualTo(USER_NAME3);


    }

    /**
     * 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
     *
     * ## 테스트 내용
     * - 회원 ROLE 검증 (ADMIN, USER)
     */
    @Test
    @DisplayName("회원가입 service2")
    public void signUp2(){
        // given
        UserDto userDto1 = new UserDto();
        userDto1.setName(USER_NAME);
        userDto1.setPassword(VALID_USER_PASSWORD);
        userDto1.setRole(UserRole.ADMIN);

        UserDto userDto2 = new UserDto();
        userDto2.setName(USER_NAME2);
        userDto2.setPassword(VALID_USER_PASSWORD);
        userDto2.setRole(UserRole.USER);

        User user1 = new User(userDto1);
        User user2 = new User(userDto2);

        // stub
        when(userRepository.findByName(userDto1.getName())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user1);

        // when
        UserDto tc_userDto = authService.signUp(userDto1);

        // then
        assertThat(tc_userDto.getRole()).isEqualTo(UserRole.ADMIN);


        // stub
        when(userRepository.findByName(userDto2.getName())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user2);

        // when
        UserDto tc_userDto2 = authService.signUp(userDto2);

        // then
        assertThat(tc_userDto2.getRole()).isEqualTo(UserRole.USER);


    }

    /**
     * DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    */
    @Test
    @DisplayName("로그인 service")
    public void signIn() {
        // given
        UserDto userDto1 = new UserDto();
        userDto1.setName(USER_NAME);
        userDto1.setPassword(LEGACY_USER_PASSWORD);

        UserDto userDto2 = new UserDto();
        userDto2.setName(USER_NAME2);
        userDto2.setPassword(LEGACY_USER_PASSWORD);

        // 로그인 할 유저
        User user1 = new User(userDto1);
        user1.setPassword(passwordEncoder.encode(userDto1.getPassword()));


        User user2 = new User(userDto2);

        // invalid 유저
        UserDto userDto3 = new UserDto();
        userDto3.setName(USER_NAME);
        userDto3.setPassword("invalidPassword");

        // stub
        when(userRepository.findByName(user1.getName())).thenReturn(Optional.of(user1));
        when(userRepository.findByName(user2.getName())).thenReturn(Optional.empty());


        // when then
        assertThat(authService.signIn(userDto1, response).getName()).isEqualTo(USER_NAME);
        assertThatThrownBy(() -> authService.signIn(userDto2, response)).isInstanceOf(EntityNotFoundException.class);

        // 비밀번호 불일치
        assertThatThrownBy(() -> authService.signIn(userDto3, response)).isInstanceOf(IllegalArgumentException.class);
    }

}
