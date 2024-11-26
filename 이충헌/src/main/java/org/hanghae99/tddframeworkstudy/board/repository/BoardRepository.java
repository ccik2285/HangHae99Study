package org.hanghae99.tddframeworkstudy.board.repository;


import org.hanghae99.tddframeworkstudy.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
