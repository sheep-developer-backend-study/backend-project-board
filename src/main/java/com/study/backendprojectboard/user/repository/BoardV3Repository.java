package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.BoardV3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardV3Repository extends JpaRepository<BoardV3, Long> {

}
