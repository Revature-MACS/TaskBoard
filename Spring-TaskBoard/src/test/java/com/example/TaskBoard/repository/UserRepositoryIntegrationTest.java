package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.User;

import com.example.TaskBoard.entity.User.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserRepositoryIntegrationTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryIntegrationTest(UserRepository userRepo){this.userRepository = userRepo;}

    @Test
    public void findUserByEmailPositiveTest() {
        final String USER_EMAIL = "test@email.com";

        User temp = new User();
        temp.setEmail(USER_EMAIL);
        temp.setName("Test User");
        temp.setPassword("password");
        temp.setRole(UserRole.TESTER);

        userRepository.save(temp);

        Optional<User> output = userRepository.findUserByEmail(USER_EMAIL);
        assertTrue(output.isPresent());
        assertEquals(USER_EMAIL, output.get().getEmail());
    }

    @Test
    public void findUserByEmailNegativeTest(){
        Optional<User> output = userRepository.findUserByEmail("not@email.net");
        assertTrue(output.isEmpty());
    }

    @Test
    public void findUserByEmailAndPasswordPositiveTest() {
        final String USER_EMAIL = "test@email.com";
        final String USER_PASS = "password";

        User temp = new User();
        temp.setEmail(USER_EMAIL);
        temp.setName("Test User");
        temp.setPassword(USER_PASS);
        temp.setRole(UserRole.TESTER);

        userRepository.save(temp);

        Optional<User> output = userRepository.findUserByEmailAndPassword(USER_EMAIL, USER_PASS);
        assertTrue(output.isPresent());
        assertEquals(USER_EMAIL, output.get().getEmail());
        assertEquals(USER_PASS, output.get().getPassword());
    }

    @Test
    public void findUserByEmailAndPasswordNegativeTest(){
        Optional<User> output = userRepository.findUserByEmailAndPassword("not@email.net", "notP@assword");
        assertTrue(output.isEmpty());
    }

    @Test
    public void deleteUserByEmailPositiveTest() {
        final String USER_EMAIL = "test@email.com";

        User temp = new User();
        temp.setEmail(USER_EMAIL);
        temp.setName("Test User");
        temp.setPassword("password");
        temp.setRole(UserRole.TESTER);
        userRepository.save(temp);

        List<User> userList = userRepository.findAll();
        userRepository.deleteUserByEmail(USER_EMAIL);

        assertNotEquals(userList, userRepository.findAll());
    }

    @Test
    public void deleteUserByEmailNegativeTest(){
        User temp = new User();
        temp.setEmail("test@email.com");
        temp.setName("Test User");
        temp.setPassword("password");
        temp.setRole(UserRole.TESTER);
        userRepository.save(temp);

        List<User> userList = userRepository.findAll();
        userRepository.deleteUserByEmail("not@email.net");

        assertEquals(userList, userRepository.findAll());
    }

}
