package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
                                                    //<entity, PK type>
    Page<Board> findByTitleContainingOrContentContaining(String searchKeywordTitle, String searchKeywordContent, Pageable pageable);
    Page<Board> findByTitleContaining(String searchKeywordTitle, Pageable pageable);
    Page<Board> findByContentContaining(String searchKeywordContent, Pageable pageable);
}
