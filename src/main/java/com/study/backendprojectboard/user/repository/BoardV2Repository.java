package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.BoardV2;
import com.study.backendprojectboard.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardV2Repository extends JpaRepository<BoardV2, Long> {

    List<BoardV2> findAllByTitleContains(String title);

    List<BoardV2> findAllByUser(User user);

    List<BoardV2> findAllByOrderByRegDateDesc();

    List<BoardV2> findAllByUserOrderByRegDateDesc(User user);

    List<BoardV2> findAllByOrderByBoardIdDesc();

    List<BoardV2> findAllByUserOrderByBoardIdDesc(User user);
}
