package com.study.backendprojectboard.jpaTest;

import com.study.backendprojectboard.user.model.Board;
import com.study.backendprojectboard.user.model.BoardV2;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.BoardRepository;
import com.study.backendprojectboard.user.repository.BoardV2Repository;
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
public class UserAndBoardTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardV2Repository boardV2Repository;

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
                .build()));
        log.info("## 추가된 사용자 List [{}]", userList);
        return userList;
    }
    @Test
    public void BOARD_생성() {
        List<User> userList = user_insert();

        Board board = new Board();
        board.setUserId(userList.get(0).getUserId());
        board.setTitle("title");
        board.setContent("content");
        board.setRegDate(LocalDateTime.now());
        board.setUptDate(LocalDateTime.now());

        boardRepository.save(board);

        //select board_id
        //from board
        // where  board_id=1

        Optional<Board> savedBoard = boardRepository.findById(board.getBoardId());
        log.info("insert board [{}], [{}]", board, savedBoard.get());
        Assertions.assertTrue(savedBoard.get().getTitle().equals(board.getTitle()));
    }

    @Test
    public void BOARD_생성_여러개() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            Board board = new Board();
            board.setUserId(userList.get(0).getUserId());
            board.setTitle("title" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardRepository.save(board);
        }

        //select *
        //from board

        List<Board> boardList = boardRepository.findAll();
        log.info("insert board list [{}]", boardList);
        Assertions.assertEquals(index, boardList.size());
    }

    @Test
    public void BOARD_검색() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            Board board = new Board();
            board.setUserId(userList.get(0).getUserId());
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardRepository.save(board);
        }


        //select *
        //from board
        // where  titlt = title

        List<Board> boardList = boardRepository.findAllByTitleContains("title");
        log.info("insert board list [{}]", boardList);
        Assertions.assertEquals(index / 2, boardList.size());
    }

    @Test
    public void BOARD_사용자_list() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            Board board = new Board();
            board.setUserId(i % 2 == 0 ? userList.get(0).getUserId() : userList.get(1).getUserId());
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardRepository.save(board);
        }


        List<Board> boardList = boardRepository.findAllByUserId(userList.get(0).getUserId());
        log.info("insert board list [{}]", boardList);
        Assertions.assertTrue(boardList.get(0).getTitle().contains("title"));
    }

    @Test
    public void BOARD_LIST_최신순() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            Board board = new Board();
            board.setUserId(i % 2 == 0 ? userList.get(0).getUserId() : userList.get(1).getUserId());
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardRepository.save(board);
        }


        List<Board> boardList = boardRepository.findAllByOrderByBoardIdDesc();
                //boardRepository.findAllByOrderByRegDateDesc();
        log.info("insert board list [{}]", boardList);
        Assertions.assertEquals(10L, boardList.get(0).getBoardId());
    }

    @Test
    public void BOARD_LIST_USER_ID_최신순() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            Board board = new Board();
            board.setUserId(i % 2 == 0 ? userList.get(0).getUserId() : userList.get(1).getUserId());
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardRepository.save(board);
        }

        // select
        // from board
        // where userid=1
        // order by BoardId Desc


        List<Board> boardList = boardRepository.findAllByUserIdOrderByBoardIdDesc(userList.get(0).getUserId());
        // boardRepository.findAllByUserIdOrderByRegDateDesc();
        log.info("insert board list [{}]", boardList);
        Assertions.assertEquals(9L, boardList.get(0).getBoardId());
    }


    @Test
    public void BOARD_V2_생성() {
        List<User> userList = user_insert();

        BoardV2 board = new BoardV2();
        board.setUser(userList.get(0));
        board.setTitle("title");
        board.setContent("content");
        board.setRegDate(LocalDateTime.now());
        board.setUptDate(LocalDateTime.now());

        boardV2Repository.save(board);

        //select *
        //from boardv2
        //where boardid =1

        Optional<BoardV2> savedBoard = boardV2Repository.findById(board.getBoardId());
        log.info("insert board v2 [{}], [{}]", board, savedBoard.get());
        Assertions.assertTrue(savedBoard.get().getTitle().equals(board.getTitle()));
    }

    @Test
    public void BOARD_V2_생성_여러개() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            BoardV2 board = new BoardV2();
            board.setUser(userList.get(0));
            board.setTitle("title" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardV2Repository.save(board);
        }

        //select *
        //from boardv2


        List<BoardV2> boardList = boardV2Repository.findAll();
        log.info("insert board v2 list [{}]", boardList);
        Assertions.assertEquals(index, boardList.size());
    }

    @Test
    public void BOARD_V2_검색() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            BoardV2 board = new BoardV2();
            board.setUser(userList.get(0));
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardV2Repository.save(board);
        }

        //select *
        //from boardv2
        //where title like '%title%'
        List<BoardV2> boardList = boardV2Repository.findAllByTitleContains("title");
        log.info("insert board v2 list [{}]", boardList);
        Assertions.assertEquals(index / 2, boardList.size());
    }

    @Test
    public void BOARD_V2_사용자_list() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            BoardV2 board = new BoardV2();
            board.setUser(i % 2 == 0 ? userList.get(0) : userList.get(1));
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardV2Repository.save(board);
        }
        //select *
        //from boardv2
        //where user_id =1;

        List<BoardV2> boardList = boardV2Repository.findAllByUser(userList.get(0));
        log.info("insert board v2 list [{}]", boardList);
        Assertions.assertTrue(boardList.get(0).getTitle().contains("title"));
    }

    @Test
    public void BOARD_V2_LIST_최신순() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            BoardV2 board = new BoardV2();
            board.setUser(i % 2 == 0 ? userList.get(0) : userList.get(1));
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardV2Repository.save(board);
        }

        // select *
        // from boardv2
        // order by board_id desc


        List<BoardV2> boardList = boardV2Repository.findAllByOrderByBoardIdDesc();
        //boardV2Repository.findAllByOrderByRegDateDesc();
        log.info("insert board v2 list [{}]", boardList);
        Assertions.assertEquals(10L, boardList.get(0).getBoardId());
    }

    @Test
    public void BOARD_V2_LIST_USER_ID_최신순() {
        List<User> userList = user_insert();

        int index = 10;
        for (int i = 0; i < index; i++) {
            BoardV2 board = new BoardV2();
            board.setUser(i % 2 == 0 ? userList.get(0) : userList.get(1));
            board.setTitle(i % 2 == 0 ? "title" + i : "test" + i);
            board.setContent("content" + i);
            board.setRegDate(LocalDateTime.now());
            board.setUptDate(LocalDateTime.now());

            boardV2Repository.save(board);
        }

        // select *
        // from boardv2
        // where user_id =1
        // order by board_id desc


        List<BoardV2> boardList = boardV2Repository.findAllByUserOrderByBoardIdDesc(userList.get(0));
        // boardV2Repository.findAllByUserOrderByRegDateDesc();
        log.info("insert board list [{}]", boardList);
        Assertions.assertEquals(9L, boardList.get(0).getBoardId());
    }
}
