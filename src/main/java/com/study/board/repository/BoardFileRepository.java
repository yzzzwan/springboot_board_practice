package com.study.board.repository;

import com.study.board.entity.Board;
import com.study.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
    List<BoardFile> findAllByBoard_BoardId(Integer boardId);

    boolean existsByBoard_BoardId(Integer boardId);

    void deleteByBoard_BoardId(Integer boardId);

}
