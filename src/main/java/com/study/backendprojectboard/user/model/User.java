package com.study.backendprojectboard.user.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false)
    private String email;
}
