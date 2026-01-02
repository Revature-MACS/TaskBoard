package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.repository.CommentsRepository;
import com.example.TaskBoard.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, IssueRepository issueRepository) {
        this.commentsRepository = commentsRepository;
        this.issueRepository = issueRepository;
    }

    public List<Comments> readComments(){
        return commentsRepository.findAll();
    }

    private Optional<Issue> checkIfIssueExist(UUID issueId){
        return issueRepository.findById(issueId);
    }

    public List<Comments> readCommentByIssue(UUID issueId){
        Optional<Issue> issue = checkIfIssueExist(issueId);
        return commentsRepository.findAllByIssue(issue);
    }

    public Comments createComment(Comments comments){
        return commentsRepository.save(comments);
    }

    public Comments updateComment(Comments comments){
        return commentsRepository.save(comments);
    }

    public void removeComment(Long commentId){
        commentsRepository.deleteById(commentId);
    }
}
