package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member>findByEmail(String email);
    Member findByName (String name);
    Member findByEmailAndPassword(String email, String password);
}
