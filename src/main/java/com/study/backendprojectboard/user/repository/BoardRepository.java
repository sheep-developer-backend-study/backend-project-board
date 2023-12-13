package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByTitleContains(String title);

    List<Board> findAllByUserId(Long userId);

    List<Board> findAllByOrderByRegDateDesc();

    List<Board> findAllByUserIdOrderByRegDateDesc(Long userId);

    List<Board> findAllByOrderByBoardIdDesc();

    List<Board> findAllByUserIdOrderByBoardIdDesc(Long userId);
}
