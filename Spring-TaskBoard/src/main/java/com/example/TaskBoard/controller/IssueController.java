package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class IssueController {

    private IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    // POST - create Issue
    @PostMapping("/issues")
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue, @RequestHeader String authorization){
        Issue createdIssue = issueService.createIssue(issue, authorization);
        if(createdIssue != null){
            return ResponseEntity.status(HttpStatus.OK).body(createdIssue);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

    }

    // GET - Get Issue by ID
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable String issueId){
        UUID issueUUID = UUID.fromString(issueId);
        Issue optionalIssue = issueService.findByIssueId(issueUUID);
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
        if(issueService.findByIssueId(UUID.fromString(issueId)) != null){
            issueService.deleteIssue(UUID.fromString(issueId));
            return ResponseEntity.status(204).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT - Update issue by ID
    @PutMapping("issues/{issueId}")
    public ResponseEntity<Issue> updateIssueById(@PathVariable String issueId, @RequestBody Issue issue){
        issue.setIssueId(UUID.fromString(issueId));
        Issue updatedIssue = issueService.updateIssue(issue);
        return ResponseEntity.status(HttpStatus.OK).body(updatedIssue);
    }
}
