package com.study.backendprojectboard.jpaTest;

import com.study.backendprojectboard.user.model.Board;
import com.study.backendprojectboard.user.model.BoardV5;
import com.study.backendprojectboard.user.model.Comment;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.BoardV5Repository;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Slf4j

public class BoardV5Test {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardV5Repository boardV5Repository;

    public List<User> user_insert() {
        List<User> userList = new ArrayList<>();
        userList.add(userRepository.save(User.builder()
                .name("test")
                .password("testpwd")
                .email("test@email.com")
                .build())
        );
        userList.add(userRepository.save(User.builder()
                .name("test2")
                .password("testpwd2")
                .email("test2@email.com")
                .build())
        );

        userList.add(userRepository.save(User.builder()
                .name("test3")
                .password("testpwd")
                .email("test3@email.com")
                .build())
        );
        userList.add(userRepository.save(User.builder()
                .name("test4")
                .password("testpwd")
                .email("test4@email.com")
                .build())
        );
        log.info("## 추가된 사용자 List [{}]", userList);
        return userList;


    }

    @Test
    public void BOARD_V5_생성() {
        List<User> userList = user_insert();

        BoardV5 boardV5 = new BoardV5();
        boardV5.setUser(userList.get(0));
        boardV5.setTitle("tit2222le");
        boardV5.setContent("갈갈갈");
        boardV5.setRegDate(LocalDateTime.now());

        boardV5Repository.save(boardV5);

        Optional<BoardV5> savedBoard = boardV5Repository.findById(boardV5.getBoardId());
        log.info("insert board [{}], [{}]", boardV5, savedBoard.get());
        Assertions.assertTrue(savedBoard.get().getTitle().equals(boardV5.getTitle()));


    }

    @Test
    public void BOARD_V5_생성_여러개(){

    int index = 10;
        for (int i = 0; i < index; i++) {
            List<User> userList =user_insert();
            List<Comment> commentList = new ArrayList<>();
            Comment comment =new Comment();
            BoardV5 boardV5 = new BoardV5();
            Long userId=userList.get(0).getUserId();
            boardV5.setUser(userList.get(0));
            boardV5.setTitle("title");
            boardV5.setContent("content");
            boardV5.setCommentList((commentList));
            boardV5.setRegDate(LocalDateTime.now());
            boardV5.setUpDate(LocalDateTime.now());

            boardV5Repository.save(boardV5);
        }
        List<BoardV5> boardV5List =boardV5Repository.findAll();
        log.info("insert board list [{}]", boardV5List);
        Assertions.assertEquals(index, boardV5List.size());
    }
@Test
    public void BOARD_검색(){
        int index = 10;
    for (int i = 0; i < index ; i++) {
        List<User> userList =user_insert();
        BoardV5 boardV5 = new BoardV5();
        boardV5.setUser(userList.get(0));
        Long userid=(userList.get(0).getUserId());
        boardV5.setTitle(i%2==0 ? "title"+i : "test"+i);
        boardV5.setContent("content"+i);
        boardV5.setRegDate(LocalDateTime.now());
        boardV5.setUpDate(LocalDateTime.now());

        boardV5Repository.save(boardV5);
    }
        List<BoardV5>boardV5List = boardV5Repository.findAllByTitleContains("title");
        Assertions.assertEquals(index/2,boardV5List.size());
    }
    @Test
    public void BOARD_사용자_list(){
        List<User> userList =user_insert();

        int index = 10;
        for (int i = 0; i < index ; i++) {
            BoardV5 boardV5 = new BoardV5();


            boardV5.setUser(userList.get(0));

            boardV5.setTitle(i%2==0 ? "title"+i : "test"+i);
            boardV5.setContent("content"+i);
            boardV5.setRegDate(LocalDateTime.now());
            boardV5.setUpDate(LocalDateTime.now());

            boardV5Repository.save(boardV5);
        }
        List<BoardV5>boardV5List=boardV5Repository.findAllByUser(userList.get(0));
        Assertions.assertTrue(boardV5List.get(0).getTitle().contains("title"));

    }

    @Test
    public void BOARD_LIST_USER_ID_최신순(){
        List<User> userList =user_insert();

        int index = 10;
        for (int i = 0; i < index ; i++) {
            BoardV5 boardV5 = new BoardV5();

            boardV5.setUser(userList.get(0));

            boardV5.setTitle(i%2==0 ? "title"+i : "test"+i);
            boardV5.setContent("content"+i);
            boardV5.setRegDate(LocalDateTime.now());
            boardV5.setUpDate(LocalDateTime.now());

            boardV5Repository.save(boardV5);
        }
        List<BoardV5>boardV5List=boardV5Repository.findAllByUserOrderByBoardIdDesc(userList.get(0));
        Assertions.assertTrue(boardV5List.get(0).getTitle().contains("test"));

    }














//    @Test
//    public void BOARD_사용자_list(){
//        List<User> userList =user_insert();
//        BoardV5 boardV5 = new BoardV5();
//        boardV5.setUser(userList.get(3));
//        boardV5.setTitle("star");
//        boardV5.setRegDate(LocalDateTime.now());
//        Comment comment = new Comment();
//        comment.setContent("돌라돌라");
//
//
//        boardV5.getCommentList().add(comment);
//        boardV5Repository.save(boardV5);
//        List<BoardV5>boardV5List=boardV5Repository.findAllByUserIdOrderByBoardIdDesc(userList.get(0));
//        log.info("insert board list [{}]", boardList);
//        Assertions.assertEquals(9L, boardList.get(0).getBoardId());
//    }


}
