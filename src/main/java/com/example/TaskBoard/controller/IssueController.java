package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class IssueController {

    private IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    /*
        All endpoints will likely change in the future to be built
        upon project endpoint "/project/issue/..."
     */

    // POST - create Issue
    @PostMapping("/issues")
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue){
        Issue createdIssue;
        createdIssue = issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.OK).body(createdIssue);
    }

    // GET - Get Issue by ID
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable String issueId){
        UUID issueUUID = UUID.fromString(issueId);
        Issue optionalIssue;

        optionalIssue = issueService.findByIssueId(issueUUID);
        if(optionalIssue != null) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalIssue);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // GET - Get all issues
    @GetMapping("/issues")
    public ResponseEntity<List<Issue>> getIssues(){
        List<Issue> issues = issueService.retrieveIssues();
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    // DELETE - Delete issue by ID
    @DeleteMapping("issues/{issueId}")
    public ResponseEntity<Void> deleteIssueById(@PathVariable String issueId){
        UUID issueUUID = UUID.fromString(issueId);
        Issue optionalIssue;

        if(issueService.findByIssueId(issueUUID) != null){
            issueService.deleteIssue(issueUUID);
            return ResponseEntity.status(204).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("issues/{issueId}")
    public ResponseEntity<Issue> updateStatusByIssueId(@PathVariable String issueId, @RequestBody Map<String, Integer> status){
        UUID issueUUID = UUID.fromString(issueId);
        Issue optionalIssue;
        int newStatusId;
        newStatusId = status.get("status");

        optionalIssue = issueService.updateStatus(issueUUID, newStatusId);
        if (optionalIssue != null){
            return ResponseEntity.status(HttpStatus.OK).body(optionalIssue);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
