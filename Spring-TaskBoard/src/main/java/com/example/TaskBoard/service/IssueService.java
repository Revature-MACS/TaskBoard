package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.AuditLog;
import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final AuthService authService;
    private final AuditLogService auditLogService;
    private final UserRepository userRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, AuthService authService, AuditLogService auditLogService, UserRepository userRepository){
        this.issueRepository = issueRepository;
        this.authService = authService;
        this.auditLogService = auditLogService;
        this.userRepository = userRepository;
    }

    public Issue createIssue(Issue issue, String headerData) throws SQLException {
        if(authService.getAuthLevel(headerData).equals(User.UserRole.TESTER)){
            User owner = userRepository.findUserByEmail(issue.getOwner().getEmail())
                    .orElseThrow(() -> new RuntimeException("Owners not Found"));

            try {
                issue.setOwner(owner);
                Issue savedIssue = issueRepository.save(issue);

                // Audit log
                auditLogService.logIssueAction(
                        savedIssue.getIssueId().toString(),
                        AuditLog.ActionType.CREATE,
                        owner.getEmail(),
                        "Created project: " + savedIssue.getTitle()
                );
                return savedIssue;
            } catch (Exception e) {
                throw new SQLException("Cant have Null Value");
            }
        }
        else {
            return null;
        }

    }

    public void deleteIssue(UUID issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        String issueName = issue.map(Issue::getTitle).orElse("Unknown");
        String ownerEmail = issue.map(i -> i.getOwner().getEmail()).orElse("Unknown");

        issueRepository.deleteById(issueId);

        // Audit log
        auditLogService.logIssueAction(
                issueId.toString(),
                AuditLog.ActionType.DELETE,
                ownerEmail,
                "Deleted Issue: " + issueName
        );
    }

    public List<Issue> retrieveIssues(){
        return issueRepository.findAll();
    }

    public Issue findByIssueId(UUID issueId){
        Optional<Issue> issue = issueRepository.findById(issueId);
        return issue.orElse(null);
    }

    public List<Issue> getIssuesByProject(UUID projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public List<AuditLog> getIssueHistory(UUID issueId) {
        return auditLogService.getAuditLogsForEntity(AuditLog.EntityType.ISSUE, issueId.toString());
    }

    public Issue updateIssue(Issue issue, String headerData) {
        // Track owner + Auth Level
        User.UserRole userRole = authService.getAuthLevel(headerData);
        User owner = userRepository.findUserByEmail(issue.getOwner().getEmail())
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found"));

        Optional<Issue> prevIssue = issueRepository.findById(issue.getIssueId());
        Issue previousIssue;
        if(prevIssue.isPresent())
        {
            previousIssue = prevIssue.get();
            issue.setOwner(owner);
            issue.setProjectId(previousIssue.getProjectId());
        }
        else {
            return null;
        }

        // If we change the status
        if(!previousIssue.getStatus().equals(issue.getStatus()))
        {
            // To Open or Closed
            if(issue.getStatus().equals(Issue.IssueStatus.OPEN) || issue.getStatus().equals(Issue.IssueStatus.CLOSED))
            {
                // And we're a tester
            if(userRole.equals(User.UserRole.TESTER))
                {
                    //update repo
                    auditLogService.logIssueAction(
                            issue.getIssueId().toString(),
                            AuditLog.ActionType.UPDATE,
                            owner.getEmail(),
                            "Status changed from " + previousIssue.getStatus() + " to " + issue.getStatus()
                    );
                    return issueRepository.save(issue);
                }
                else {
                    throw new InvalidAuthorizationException("Invalid Authorization");
                }
            } // To In progress or Resolved
            else if(issue.getStatus() == Issue.IssueStatus.IN_PROGRESS || issue.getStatus() == Issue.IssueStatus.RESOLVED)
            {
                // And we're a dev
                if(userRole.equals(User.UserRole.DEVELOPER))
                {
                    //update repo
                    auditLogService.logIssueAction(
                            issue.getIssueId().toString(),
                            AuditLog.ActionType.UPDATE,
                            owner.getEmail(),
                            "Status changed from " + previousIssue.getStatus() + " to " + issue.getStatus()
                    );
                    return issueRepository.save(issue);
                }
                else {
                    throw new InvalidAuthorizationException("Invalid Authorization");
                }
            }
        }
        auditLogService.logIssueAction(
                issue.getIssueId().toString(),
                AuditLog.ActionType.UPDATE,
                owner.getEmail(),
                "Updated Issue: " + issue.getTitle()
        );
        return issueRepository.save(issue);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public class InvalidAuthorizationException extends RuntimeException {
        public InvalidAuthorizationException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class OwnerNotFoundException extends RuntimeException {
        public OwnerNotFoundException(String message) {
            super(message);
        }
    }

}
