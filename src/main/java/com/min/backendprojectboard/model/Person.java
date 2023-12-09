package com.min.backendprojectboard.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "member")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "personIdex")
    private Long personId;
    @Column(length = 20)
    private String name;
    @Column
    private String country;
}
