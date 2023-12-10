package com.study.backendprojectboard.board.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Data
@Entity
@Table (name = "board")

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardId;
    @Column
    private Long userId;
    @Column (length = 50)
    private String title;
    @Column
    private LocalDateTime creatAt;
    @Column
    private LocalDateTime updateAt;


}
