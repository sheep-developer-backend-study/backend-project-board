package com.study.backendprojectboard.jpaTest;

import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Slf4j
public class JpaTest2 {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userinsert() {
        User user = new User();
        user.setName("갈갈갈");
        user.setEmail("alstmddn11@naver.com");
        user.setPassword("1234");
        userRepository.save(user); //인설트 업데이트 동시에 제공 save 함수
        List<User> userList = userRepository.findAll(); //전체 셀렉트
        Assertions.assertEquals(1, userList.size());
        Assertions.assertTrue(userList.get(0).getName().equals("갈갈갈"));


    }

    @Test
    public void userInsertAll() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("갈갈갈"+i);
            user.setEmail("alstmddn11@naver.com"+i);
            user.setPassword("1234"+i);
            list.add(user);

        }
        userRepository.saveAll(list);
        List<User> userList = userRepository.findAll(); //전체 셀렉트
        userList.get(3);
        Assertions.assertTrue(userList.get(3).getName().equals("갈갈갈3"));
        log.info("userList [{}]", userList);
    }
    @Test
    public void USER_SELECT_BY_ID() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        Optional<User> user1 = userRepository.findById(1L);
        Assertions.assertEquals(user.getName(), user1.get().getName());
    }
    @Test
    public void USER_SELECT_BY_Name() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        //select * from t_user where name = ?;
        Optional<User> user1 = userRepository.findByName("test");
        Assertions.assertEquals(user.getName(), user1.get().getName());
    }
}
