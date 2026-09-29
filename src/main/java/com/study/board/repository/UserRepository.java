package com.study.board.repository;

import com.study.board.entity.User;
import org.slf4j.MDC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLoginId(String id);
}
