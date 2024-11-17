package org.hanghae99.tddframeworkstudy.userjoin.repository;

import org.hanghae99.tddframeworkstudy.userjoin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByUsername(String username);

}
