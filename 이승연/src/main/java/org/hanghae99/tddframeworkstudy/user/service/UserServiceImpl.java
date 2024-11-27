package org.hanghae99.tddframeworkstudy.user.service;

import org.hanghae99.tddframeworkstudy.user.entity.UserEntity;
import org.hanghae99.tddframeworkstudy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 중복된 사용자 이름이 있는지 확인
    @Override
    public boolean isDuplicateUsername(String username) {

        return userRepository.findByUsername(username).isPresent();
    }

    // 사용자 저장
    @Override
    public UserEntity save(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }
}
