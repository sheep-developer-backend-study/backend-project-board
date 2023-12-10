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
public class JpaCRUDTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void USER_INSERT() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        List<User> userList = userRepository.findAll();
        Assertions.assertEquals(1, userList.size());
    }

    @Test
    public void USER_INSERT_ALL() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("test" + i);
            user.setPassword("pwd");
            user.setEmail("test" + i + "@email.com");
            list.add(user);
        }

        userRepository.saveAll(list);

        List<User> userList = userRepository.findAll();
        Assertions.assertEquals(10, userList.size());
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
    public void USER_SELECT_BY_NAME() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        User user1 = userRepository.findByName("test");
        Assertions.assertEquals(user.getName(), user1.getName());
    }
    @Test
    public void USER_SELECT_BY_EMAIL_AND_PASSWORD() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        User user1 = userRepository.findByEmailAndPassword("test@email.com", "pwd");
        Assertions.assertEquals(user.getName(), user1.getName());
    }

    @Test
    public void USER_UPDATE() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        User user1 = userRepository.findByEmailAndPassword("test@email.com", "pwd");
        Assertions.assertEquals(user.getName(), user1.getName());

        user1.setName("test2");
        userRepository.save(user1);

        User user2 = userRepository.findByEmailAndPassword("test@email.com", "pwd");
        Assertions.assertEquals("test2", user2.getName());
    }

    @Test
    public void USER_DELETE() {
        User user = new User();
        user.setName("test");
        user.setPassword("pwd");
        user.setEmail("test@email.com");

        userRepository.save(user);

        User user1 = userRepository.findByEmailAndPassword("test@email.com", "pwd");
        Assertions.assertEquals(user.getName(), user1.getName());

        userRepository.delete(user1);

        User user2 = userRepository.findByEmailAndPassword("test@email.com", "pwd");
        Assertions.assertTrue(user2 == null);
    }
}
