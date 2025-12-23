package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {

    private IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository){
        this.issueRepository = issueRepository;
    }

    /*
        Needs to be updated in future so only Testers can create 'Issues'
     */
    public Issue createIssue(Issue issue){
        return issueRepository.save(issue);
    }

    public void deleteIssue(UUID issueId){
        issueRepository.deleteById(issueId);
    }

    public List<Issue> retrieveIssues(){
        return issueRepository.findAll();
    }

    public Issue findByIssueId(UUID issueID){
        Optional<Issue> issue = issueRepository.findById(issueID);
        return issue.orElse(null);

    }

    public Issue updateStatus(UUID issueID, int status){
        Optional<Issue> optionalIssue = issueRepository.findById(issueID);
        if(optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();
            issue.setStatus(status);
            return issueRepository.save(issue);
        }
        else {
            return null;
        }
    }

    public void updatePriority(UUID issueID, int priority){
        Optional<Issue> optionalIssue = issueRepository.findById(issueID);
        if(optionalIssue.isPresent())
        {
            Issue issue = optionalIssue.get();
            issue.setPriority(priority);
            issueRepository.save(issue);
        }
    }

    public void updateSeverity(UUID issueID, int severity){
        Optional<Issue> optionalIssue = issueRepository.findById(issueID);
        if(optionalIssue.isPresent())
        {
            Issue issue = optionalIssue.get();
            issue.setSeverity(severity);
            issueRepository.save(issue);
        }
    }

    /*
        Need to add service to retrieve, add, and delete comments in future
     */












}
