package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.BoardV5;
import com.study.backendprojectboard.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardV5Repository extends JpaRepository<BoardV5,Long> {

List<BoardV5>findAllByTitleContains(String title);
List<BoardV5>findAllByUser(User user);

List<BoardV5>findAllByUserOrderByBoardIdDesc(User user);

}
