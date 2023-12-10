package com.study.backendprojectboard.board.repository;

import com.study.backendprojectboard.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

}
