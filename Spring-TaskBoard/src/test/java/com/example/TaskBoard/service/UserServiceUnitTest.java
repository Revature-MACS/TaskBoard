package com.example.TaskBoard.service;

import com.example.TaskBoard.dto.Token;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.entity.User.UserRole;
import com.example.TaskBoard.repository.UserRepository;
import com.example.TaskBoard.util.TokenUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenUtility tokenUtility;
    @InjectMocks
    private UserService userService;

    @Test
    public void getAllUsersTest(){
        ArrayList<User> userList = new ArrayList<>();

        User testUser1 = new User();
        testUser1.setUserID(UUID.randomUUID());

        User testUser2 = new User();
        testUser2.setUserID(UUID.randomUUID());

        userList.add(testUser1);
        userList.add(testUser2);

        /*
         Simply passing userList causes the List to be passed by
         reference and does NOT create a copy. Thus manipulating userList or returnUsers
         would inadvertently modify the other.
        */
        when(userRepository.findAll()).thenReturn(new ArrayList<>(userList));

        List<User> returnUsers = userService.getAllUsers();


        /*
        Two null arrays will result in a pass for assertArrayEquals
        so make sure at least one array is not null
        */
        assertNotNull(returnUsers);
        assertArrayEquals(userList.toArray(), returnUsers.toArray());
    }

    @Test
    // This is a useful sanity test to ensure that the userRepository is being initialized and called correctly
    public void getAllUsersEmptyTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> returnUsers = userService.getAllUsers();

        assertNotNull(returnUsers);
        assertTrue(returnUsers.isEmpty());
    }

    @Test
    public void getUserByIdPositiveTest(){
        User testUser = new User();
        UUID userID = UUID.randomUUID();
        testUser.setUserID(userID);

        when(userRepository.findById(userID)).thenReturn(Optional.of(testUser));

        Optional<User> returnUser = userService.getUserById(userID);

        assertTrue(returnUser.isPresent());
        assertEquals(testUser.getUserID(), returnUser.get().getUserID());
    }

    @Test
    public void createNewUserPositiveTest() throws SQLException {
        final String TOKEN_TEXT = "Mock_Token";

        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setEmail("test@email.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        when(userRepository.save(user)).thenReturn(user);
        when(tokenUtility.generateLoginToken(user.getUserID(), UserRole.TESTER)).thenReturn(TOKEN_TEXT);

        Token returnToken = userService.createNewUser(user);

        assertNotNull(returnToken);
        assertEquals(TOKEN_TEXT, returnToken.getToken());
    }

    @Test
    public void createUserNegativeTest(){
        User invalidUser = new User();
        invalidUser.setEmail("test@email.com");
        invalidUser.setName("");
        invalidUser.setPassword("");

        when(userRepository.findUserByEmail(invalidUser.getEmail()))
                .thenReturn(Optional.of(invalidUser));

        // First check for invalid name
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createNewUser(invalidUser));
        assertEquals("The name field must not be empty!", exception.getMessage());
        invalidUser.setName("Test User");

        // Next check for invalid password
        exception = assertThrows(IllegalArgumentException.class, () -> userService.createNewUser(invalidUser));
        assertEquals("The password field must not be empty!", exception.getMessage());
        invalidUser.setPassword("password");

        // Finally check for conflict
        exception = assertThrows(SQLException.class, () -> userService.createNewUser(invalidUser));
        assertEquals("A user associated with the email " + invalidUser.getEmail() + " already exist!",
                exception.getMessage());
    }

    @Test
    public void updateUserPositiveTest() throws SQLException{
        User oldInfo = new User();
        oldInfo.setUserID(UUID.randomUUID());
        oldInfo.setEmail("test@email.com");
        oldInfo.setRole(UserRole.ADMIN);
        oldInfo.setName("Test User");
        oldInfo.setPassword("password");

        User newInfo = new User();
        newInfo.setUserID(oldInfo.getUserID());
        newInfo.setEmail("new@email.com");
        newInfo.setRole(UserRole.TESTER);
        newInfo.setName("Test User");
        newInfo.setPassword("password");

        when(userRepository.findUserByEmail("test@email.com")).thenReturn(Optional.of(oldInfo));
        when(userRepository.save(newInfo)).thenReturn(newInfo);

        User returnUser = userService.updateUser("test@email.com", newInfo);

        assertNotNull(returnUser);
        assertEquals(oldInfo.getUserID(), returnUser.getUserID());
        assertEquals(newInfo.getName(), returnUser.getName());
        assertEquals(newInfo.getPassword(), returnUser.getPassword());
        assertEquals(newInfo.getEmail(), returnUser.getEmail());
        assertEquals(newInfo.getRole(), returnUser.getRole());
    }

    @Test
    public void updateUserNegativeTest(){
        final String EMAIL = "invalid@email.com";

        User invalidUser = new User();
        invalidUser.setName("");
        invalidUser.setPassword("");
        invalidUser.setEmail(EMAIL);

        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.empty());

        // First check for invalid name
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(EMAIL, invalidUser));
        assertEquals("The name field must not be empty!", exception.getMessage());
        invalidUser.setName("Test User");

        // Next check for invalid password
        exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(EMAIL, invalidUser));
        assertEquals("The password field must not be empty!", exception.getMessage());
        invalidUser.setPassword("password");

        // Finally check for conflict
        exception = assertThrows(SQLException.class, () -> userService.updateUser(EMAIL, invalidUser));
        assertEquals("User with email " + EMAIL + " was not found!",
                exception.getMessage());
    }
}
