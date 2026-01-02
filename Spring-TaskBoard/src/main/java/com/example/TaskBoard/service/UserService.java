package com.example.TaskBoard.service;

import com.example.TaskBoard.dto.Token;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.UserRepository;
import com.example.TaskBoard.util.TokenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final TokenUtility tokenUtility;

    // A helper function that enforces basic table constraints
    private void validateUserInfo(User userInfo) throws SQLException, IllegalArgumentException{


        /*
           The name field in the table cannot be null, thus check to see if the string is empty.
           If it is, throw an IllegalArgumentException.
         */
        if(userInfo.getName().isEmpty()){
            throw new IllegalArgumentException("The name field must not be empty!");
        }

        //Ditto but for the password. Additional constraints should be considered later on
        if(userInfo.getPassword().isEmpty()) {
            throw new IllegalArgumentException("The password field must not be empty!");
        }

        // If the userInfo didn't trigger any exceptions, then it must be valid.
    }

    // GET /users - Gets all user in the database
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    // POST /users/register - Creates a new user account
    public User createNewUser(User userInfo) throws SQLException, IllegalArgumentException{
        // Querying the database may be slow, so check that the provided info is correct first
        validateUserInfo(userInfo);

        /*
           If searching for the email returns an entry, then an account associated with
           the given email must already exist. Since this is this attribute is unique,
           this would result in an SQLException.

           Keep in mind that findUserByEmail returns an Optional<User>
         */
        if(userRepo.findUserByEmail(userInfo.getEmail()).isPresent()){
            throw new SQLException("A user associated with the email " + userInfo.getEmail() + " already exist!");
        }

        return userRepo.save(userInfo);
    }

    /*
    PUT /users/{id} - Updates user information
    Performs same checks as the POST request.
    Admins can update any account, but Testers and Developers can only update their own account.
    */
    public User updateUser(String email, User userInfo) throws SQLException, IllegalArgumentException {
        /*
        Ensure that new data is valid first.
        Database access may be slow, so avoid unnecessary waiting by checking this first.
        */
        validateUserInfo(userInfo);

        Optional<User> oldUserDetails = userRepo.findUserByEmail(email);

        // Ensure that a user with this email already exists
        if(oldUserDetails.isEmpty()){
            throw new SQLException("User with email " + email + " was not found!");
        }

        // Add email to the new, valid information
        userInfo.setUserID(oldUserDetails.get().getUserID());

        return userRepo.save(userInfo);
    }

    /*
    DELETE /users/{email} - Deletes a User's account.
    Admins can delete any account, but Testers and Developers can only delete their account
    */
    public void deleteUser(String email){
        userRepo.deleteUserByEmail(email);
    }


    // GET /users/login
    public Token attemptToLogin(User userInfo) {
        /*
        This queries the database for user information that match the provided
        email and password. If the query does not return anything, the email-password
        combination is invalid and the user will not log in.

        For now, it returns the user information, if any exist.
        */

        Optional<User> loginAttempt = userRepo.findUserByEmailAndPassword(userInfo.getEmail(),
                userInfo.getPassword());

        if(loginAttempt.isPresent()){
            User user = loginAttempt.get();
            return new Token(tokenUtility.generateLoginToken(user.getUserID(), user.getRole()));
        }

        return null;
    }
}
