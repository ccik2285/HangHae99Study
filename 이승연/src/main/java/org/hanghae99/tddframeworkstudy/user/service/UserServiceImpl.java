package org.hanghae99.tddframeworkstudy.user.service;

import java.util.Optional;
import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.hanghae99.tddframeworkstudy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 이름으로 사용자 조회
    @Override
    public Optional<UserEntity> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    // 사용자 저장
    @Override
    public UserEntity save(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }
}
