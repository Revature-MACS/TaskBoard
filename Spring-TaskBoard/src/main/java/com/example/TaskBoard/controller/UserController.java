package com.example.TaskBoard.controller;

import com.example.TaskBoard.dto.Token;
import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
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
    public ResponseEntity<Token> attemptUserLogin(@RequestBody User userInfo){
        Token userAccount = userService.attemptToLogin(userInfo);

        if(userAccount != null){
            return ResponseEntity.status(HttpStatus.OK).body(userAccount);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<ProjectUser> assignUsersToProject(@RequestBody ProjectUser userAssignment){

        try {
            projectUserService.assignUserToProject(userAssignment);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }


        return ResponseEntity.status(HttpStatus.OK).body(userAssignment);
    }

}
