package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentsController{

    private final CommentsService commentService;

    public CommentsController(CommentsService commentService) {
        this.commentService = commentService;
    }

    // Get all the comments
    @GetMapping("/")
    public ResponseEntity<List<Comments>> readAllComment(){
        List<Comments> comments = commentService.readComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get all the comments for an issue based on issue ID
    @GetMapping("/projects/issues/{id}")
    public ResponseEntity<List<Comments>> readComment(@PathVariable UUID id){
        List<Comments> comments = commentService.readCommentByIssue(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    // Adding new comment to an issue referencing the issue based on the id
    @PostMapping("/projects/issue")
    public ResponseEntity<?> createComment(@RequestBody Comments comments){
        Comments newComment = commentService.createComment(comments);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    // optional but not necessary: to edit comment
    @PutMapping("/projects")
    public ResponseEntity<Comments> updateComment(@RequestBody Comments comments){
        Comments updateComments = commentService.updateComment(comments);
        return new ResponseEntity<>(updateComments, HttpStatus.OK);
    }

    //delete comment based on comment Id
    @DeleteMapping("/comments/project/issues/{id}")
    public ResponseEntity<?> removeComment(@PathVariable Long id){
        commentService.removeComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
