package com.study.backendprojectboard.service;

import com.study.backendprojectboard.user.model.Member;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.MemberRepository;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class MembersService {
    private final MemberRepository memberRepository;

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

}
