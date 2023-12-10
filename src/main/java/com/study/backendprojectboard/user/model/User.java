package com.study.backendprojectboard.user.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class User {
// 마이바티스에서 메퍼랑같은 jpa에서는 레퍼지토리가 필요하다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
}
