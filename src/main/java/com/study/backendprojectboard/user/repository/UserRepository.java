package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 마이바티스에서 메퍼랑같은 jpa에서는 레퍼지토리가 필요하다
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //select * from t_user where name = ?;
    Optional<User> findByName(String name);

    List<User> findAllByName(String name);
    
    // select * from t_user where password = ?;
    Optional<User> findByPassword(String password);

    //select * from t_user where email = ? and password=?;
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailAndPasswordAndName(String email, String password, String name);

    //like    findAllByName
}
