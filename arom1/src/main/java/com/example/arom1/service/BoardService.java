package com.example.arom1.service;

import com.example.arom1.entity.Board;
import com.example.arom1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    //게시글 전체 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    //게시글 삭제
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

    //게시글 수정, Board entity의 @Builder패턴 수정했습니다(id추가)
    public Board updateBoard(Long id, Board updatedBoard) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        Board existingBoard = optionalBoard.orElse(null);

        if (existingBoard != null) {
            existingBoard = Board.builder()
                    .id(existingBoard.getId())
                    .title(updatedBoard.getTitle())
                    .content(updatedBoard.getContent())
                    .build();
            return boardRepository.save(existingBoard);
        } else {
            return null;
        }
    }

    // 글 상세보기 기능
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    //글 작성 기능
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

}