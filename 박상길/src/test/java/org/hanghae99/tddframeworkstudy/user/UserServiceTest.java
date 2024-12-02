package org.hanghae99.tddframeworkstudy.user;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String USER_NAME = "test";
    private final String USER_PASSWORD = "test1234";

    private final String USER_NAME2 = "test2";


    @Test
    @DisplayName("이름으로 user찾기 service")
    public void findUserByUsername() {

        // given
        User user1 = new User();
        user1.setName(USER_NAME);
        user1.setPassword(USER_PASSWORD);

        User user2 = new User();
        user2.setName(USER_NAME2);
        user2.setPassword(USER_PASSWORD);

        when(userRepository.findByName(user1.getName())).thenReturn(Optional.of(user1));
        when(userRepository.findByName(user2.getName())).thenReturn(Optional.empty());

        // when
        UserDto userDto1 = userService.findByUserName(user1);

        // then
        assertThat(userDto1.getName()).isEqualTo(USER_NAME);
        assertThatThrownBy(() -> userService.findByUserName(user2)).isInstanceOf(EntityNotFoundException.class);

    }

}
