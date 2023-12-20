package com.study.backendprojectboard;

import com.study.backendprojectboard.user.model.Board;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.BoardRepository;
import com.study.backendprojectboard.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WebDataInit {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init() {
        User user = User.builder()
                .email("test@email.com")
                .password("1234")
                .name("test")
                .build();
        userRepository.save(user);

        Board board = new Board();
        board.setTitle("title!!!");
        board.setContent("content!!");
        board.setRegDate(LocalDateTime.now());
        board.setUserId(user.getUserId());

        boardRepository.save(board);
    }
}
