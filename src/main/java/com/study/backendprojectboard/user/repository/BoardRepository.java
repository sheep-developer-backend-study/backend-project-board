package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//Board 엔티티 이고 Long 은 그 엔티티의 pk값 고유식별자가 Long타입이라 저거한거
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByTitleContains(String title);

    List<Board> findAllByUserId(Long userId);

    List<Board> findAllByOrderByRegDateDesc();

    List<Board> findAllByUserIdOrderByRegDateDesc(Long userId);

    List<Board> findAllByOrderByBoardIdDesc();

    List<Board> findAllByUserIdOrderByBoardIdDesc(Long userId);
}
