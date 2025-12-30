package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.repository.IssueRepository;
import com.fasterxml.jackson.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {

    private IssueRepository issueRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public IssueService(IssueRepository issueRepository){
        this.issueRepository = issueRepository;
    }

    /*
        TODO: update so only testers can create issues
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

    /*
        TODO: once comment entity + service is complete, add features to
              add or delete comments.
     */












}
