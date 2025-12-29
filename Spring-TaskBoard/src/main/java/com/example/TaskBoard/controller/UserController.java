package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.service.ProjectService;
import com.example.TaskBoard.service.ProjectUserService;
import com.example.TaskBoard.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectUserService projectUserService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User userInfo){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createNewUser(userInfo));
        }
        catch(SQLException e){
            // SQLException only occurs in the case that an account with the email already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        /*
         Reaching this point indicates either an
         IllegalArgumentException or some other exception has occurred.
         Regardless, the record was not added to the table.
        */
        return ResponseEntity.status(400).build();
    }

    /*
    Updates the user with the email provided in the url.
    Admins can update any account, but Testers and Developers
    can only update their own accounts. (This will be added later)
    */
    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User userInfo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(email, userInfo));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        /*
         Reaching this point indicates either an
         IllegalArgumentException or some other exception has occurred.
         Regardless, the record was not added to the table.
        */
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> attemptUserLogin(@RequestBody User userInfo){
        Optional<User> userAccount = userService.attemptToLogin(userInfo);

        if(userAccount.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userAccount.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<Integer> assignUsersToProject(@RequestBody Project project,
                                                        @RequestBody List<User> users){
        /*
            Instead of stopping code execution if one of the users can't be added,
            the function adds all users it can and then returns the number of users
            successfully added to the given project in the response.
         */
        int numAddedSuccessfully = 0;
        for (User user : users) {
            try {
                projectUserService.assignUserToProject(user, project);
                numAddedSuccessfully++;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(numAddedSuccessfully);
    }

}
