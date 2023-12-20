package com.study.backendprojectboard.service;

import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    /**
     * login
     * @param email
     * @param password
     * @return Member - 로그인 성공
     * <br>    null - 로그인 실패
     */
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
