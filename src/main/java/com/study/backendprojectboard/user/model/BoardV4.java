package com.study.backendprojectboard.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_Board_V4")
public class BoardV4 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;
    @Column(name = "title",nullable = false,length = 50)
    private String title;
    @Column(name = "content",nullable = false,length = 50)
    private String content;
    @Column(name = "reg_date" )
    private LocalDateTime regDate;
    @Column(name = "upt_date")
    private LocalDateTime uptDate;
}
