package com.study.backendprojectboard.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Board_V5")
public class BoardV5 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "board_id")
    private Long boardId;
    @Column (name = "title",nullable = false,length = 50)
    private String title;
    @Column (name = "content",nullable = false,length = 50)
    private String content;
    @Column (name = "reg_date")
    private LocalDateTime regDate;
    @Column (name = "up_date")
    private LocalDateTime upDate;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "board_id",referencedColumnName = "board_id")
    private List<Comment> commentList;
}
