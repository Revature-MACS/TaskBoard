package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.CommentsRepository;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
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
        // Look up the actual User entity by email
        if(comments.getUser() != null && comments.getUser().getEmail() != null){
            User user = userRepository.findUserByEmail(comments.getUser().getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + comments.getUser().getEmail()));
            comments.setUser(user);
        }

        // Look up the actual Issue entity by issueId
        if(comments.getIssue() != null && comments.getIssue().getIssueId() != null){
            Issue issue = issueRepository.findById(comments.getIssue().getIssueId())
                    .orElseThrow(() -> new RuntimeException("Issue not found with ID: " + comments.getIssue().getIssueId()));
            comments.setIssue(issue);
        }

        return commentsRepository.save(comments);
    }

    public Comments updateComment(Comments comments){
        return commentsRepository.save(comments);
    }

    public void removeComment(Long commentId){
        commentsRepository.deleteById(commentId);
    }
}
