package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final AuthService authService;

    @Autowired
    public IssueService(IssueRepository issueRepository, AuthService authService){
        this.issueRepository = issueRepository;
        this.authService = authService;
    }

    /*
        TODO: update so only testers can create issues
     */
    public Issue createIssue(Issue issue, String headerData){
        if(authService.getAuthLevel(headerData).equals(User.UserRole.TESTER)){
            return issueRepository.save(issue);
        }
        else {
            return null;
        }
    }

    public void deleteIssue(UUID issueId){
        issueRepository.deleteById(issueId);
    }

    public List<Issue> retrieveIssues(){
        return issueRepository.findAll();
    }

    public Issue findByIssueId(UUID issueId){
        Optional<Issue> issue = issueRepository.findById(issueId);
        return issue.orElse(null);
    }

    /*
        TODO: update so only certain users can update issues
    */
    public Issue updateIssue(Issue issue) {
        return issueRepository.save(issue);
    }












}
