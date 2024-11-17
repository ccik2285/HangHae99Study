package org.hanghae99.tddframeworkstudy.userjoin.service;

import org.hanghae99.tddframeworkstudy.userjoin.entity.Member;
import org.hanghae99.tddframeworkstudy.userjoin.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JoinService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public JoinService(MemberRepository memberRepository,PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void registerUser(String username, String password) {
        // 중복 확인
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // pwd 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 엔티티 생성 및 저장
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

}
