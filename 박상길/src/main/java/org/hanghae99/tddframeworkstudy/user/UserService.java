package org.hanghae99.tddframeworkstudy.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findByUserName(UserDto user1) {
        User user = userRepository.findByName(user1.getName()).orElseThrow(EntityNotFoundException::new);
        return new UserDto(user);
    }
}
