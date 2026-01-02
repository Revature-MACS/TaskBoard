package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentsController{
    @Autowired
    private CommentsService commentService;

    // Get all the comments for an issue based on issue ID
    @GetMapping("/comments/projects/issues/{id}")
    public ResponseEntity<List<Comments>> readComment(@PathVariable Long id){
        List<Comments> comments = commentService.readCommentByIssue(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/comments/project/issue/{id}")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody Comments comments){
        commentService.createComment(id, comments);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/comments/project,{id}")
    public ResponseEntity<Comments> updateComment(@PathVariable Long id, @RequestBody Comments comments){
        Comments updateComments = commentService.updateComment(id, comments);
        return new ResponseEntity<>(updateComments, HttpStatus.ACCEPTED);
    }

    //delete comment based on comment Id
    @DeleteMapping("/comments/project/issues/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
