package com.study.board.repository;

import com.study.board.entity.Board;
import com.study.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
    BoardFile findByBoard_BoardId(Integer boardId);
}
