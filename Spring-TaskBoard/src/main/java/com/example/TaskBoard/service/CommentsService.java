package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    public List<Comments> readCommentByIssue(Long issueId){
        return commentsRepository.findAllByIssue(issueId);
    }

    public void createComment(Long issueId, Comments comments){
        commentsRepository.save(comments);
    }

    public Comments updateComment(Long issueId, Comments comments){
        return commentsRepository.save(comments);
    }

    public void deleteComment(Long commentId){
        commentsRepository.deleteById(commentId);
    }
}
