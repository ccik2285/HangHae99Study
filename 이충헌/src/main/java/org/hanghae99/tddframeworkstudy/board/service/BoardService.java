package org.hanghae99.tddframeworkstudy.board.service;

import lombok.AllArgsConstructor;
import org.hanghae99.tddframeworkstudy.board.entity.Board;
import org.hanghae99.tddframeworkstudy.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAllPosts() {
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Board updateBoard(Long id, Board updatedBoard, String password) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        if (!existingBoard.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        existingBoard.update(
                updatedBoard.getTitle(),
                updatedBoard.getAuthor(),
                updatedBoard.getContent()
        );

        return existingBoard;
    }

    public void deleteBoard(Long id, String password) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if(!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        boardRepository.delete(board);
    }

}
