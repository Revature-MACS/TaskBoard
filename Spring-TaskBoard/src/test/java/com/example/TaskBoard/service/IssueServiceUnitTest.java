package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.AuditLog;
import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
import com.example.TaskBoard.util.TokenUtility;
import io.cucumber.java.be.I;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceUnitTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private TokenUtility tokenUtility;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditLogService auditLogService;

    @Mock
    private AuthService authService;

    @InjectMocks IssueService issueService;


        @Test
    void getAllIssuesPositiveTest() {
        List<Issue> mockIssues = new ArrayList<>();

        Issue issue1 = new Issue();
        issue1.setIssueId(UUID.randomUUID());
        issue1.setProjectId(UUID.randomUUID());
        issue1.setTitle("Issue 1");
        issue1.setDescription("Issue 1 Description");
        issue1.setStatus(Issue.IssueStatus.OPEN);
        issue1.setSeverity(Issue.IssueSeverity.LOW);
        issue1.setPriority(Issue.IssuePriority.LOW);

        Issue issue2 = new Issue();
        issue2.setIssueId(UUID.randomUUID());
        issue2.setProjectId(UUID.randomUUID());
        issue2.setTitle("Issue 2");
        issue2.setDescription("Issue 2 Description");
        issue2.setStatus(Issue.IssueStatus.OPEN);
        issue2.setSeverity(Issue.IssueSeverity.LOW);
        issue2.setPriority(Issue.IssuePriority.LOW);

        mockIssues.add(issue1);
        mockIssues.add(issue2);


        when(issueRepository.findAll()).thenReturn(mockIssues);

        List<Issue> result = issueService.retrieveIssues();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Issue 1", result.get(0).getTitle());
        assertEquals("Issue 2", result.get(1).getTitle());
    }

    @Test
    void getAllIssuesEmptyTest() {
        when(issueRepository.findAll()).thenReturn(new ArrayList<>());

        List<Issue> result = issueService.retrieveIssues();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getIssueByIdPositiveTest() {
        Issue mockIssue = new Issue();
        mockIssue.setIssueId(UUID.randomUUID());
        mockIssue.setProjectId(UUID.randomUUID());
        mockIssue.setTitle("Issue 1");
        mockIssue.setDescription("Issue 1 Description");
        mockIssue.setStatus(Issue.IssueStatus.OPEN);
        mockIssue.setSeverity(Issue.IssueSeverity.LOW);
        mockIssue.setPriority(Issue.IssuePriority.LOW);

        when(issueRepository.findById(mockIssue.getIssueId())).thenReturn(Optional.of(mockIssue));

        Issue result = issueService.findByIssueId(mockIssue.getIssueId());

        assertNotNull(result);
        assertEquals(mockIssue.getIssueId(), result.getIssueId());
        assertEquals(mockIssue.getProjectId(), result.getProjectId());
        assertEquals(mockIssue.getTitle(), result.getTitle());
    }

    @Test
    void getIssueByIdNegativeTest() {
        UUID issueId = UUID.randomUUID();
        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        Issue result = issueService.findByIssueId(issueId);
        assertNull(result);
    }

    @Test
    void createIssuePositiveTest() throws SQLException {
        String headerData = "auth-header";

        User owner = new User();
        owner.setEmail("tester@example.com");

        Issue mockIssue = new Issue();
        mockIssue.setIssueId(UUID.randomUUID());
        mockIssue.setProjectId(UUID.randomUUID());
        mockIssue.setTitle("Issue 1");
        mockIssue.setDescription("Issue 1 Description");
        mockIssue.setStatus(Issue.IssueStatus.OPEN);
        mockIssue.setSeverity(Issue.IssueSeverity.LOW);
        mockIssue.setPriority(Issue.IssuePriority.LOW);
        mockIssue.setOwner(owner);

        when(authService.getAuthLevel(headerData)).thenReturn(User.UserRole.TESTER);
        when(userRepository.findUserByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(issueRepository.save(mockIssue)).thenReturn(mockIssue);

        Issue result = issueService.createIssue(mockIssue, headerData);

        assertNotNull(result);
        assertEquals(mockIssue.getIssueId(), result.getIssueId());
        assertEquals(mockIssue.getIssueId(), result.getIssueId());
    }

    @Test
    void createIssueNegativeTest() throws SQLException {
        Issue issue = new Issue();
        User owner = new User();
        owner.setEmail("developer@example.com");
        issue.setOwner(owner);

        when(authService.getAuthLevel("header")).thenReturn(User.UserRole.DEVELOPER); // NOT TESTER

        Issue result = issueService.createIssue(issue, "header");

        assertNull(result);
    }

    @Test
    void deleteIssuePositiveTest() {
            User owner = new User();
            owner.setEmail("tester@example.com");
            Issue mockIssue = new Issue();
            mockIssue.setOwner(owner);
            mockIssue.setIssueId(UUID.randomUUID());
            mockIssue.setProjectId(UUID.randomUUID());
            mockIssue.setTitle("Issue 1");
            mockIssue.setDescription("Issue 1 Description");
            mockIssue.setStatus(Issue.IssueStatus.OPEN);
            mockIssue.setSeverity(Issue.IssueSeverity.LOW);
            mockIssue.setPriority(Issue.IssuePriority.LOW);

            when(issueRepository.findById(mockIssue.getIssueId())).thenReturn(Optional.of(mockIssue));

            issueService.deleteIssue(mockIssue.getIssueId());

            verify(auditLogService).logIssueAction(mockIssue.getIssueId().toString(),
                    AuditLog.ActionType.DELETE,
                    owner.getEmail(),
                    "Deleted Issue: " + mockIssue.getTitle());
    }

    @Test
    void deleteIssueNegativeTest_IssueNotFound() {
            UUID issueId = UUID.randomUUID();

            when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

            issueService.deleteIssue(issueId);

            verify(issueRepository).deleteById(issueId);

            verify(auditLogService).logIssueAction(
                    issueId.toString(),
                    AuditLog.ActionType.DELETE,
                    "Unknown",
                    "Deleted Issue: Unknown"
            );
    }

    @Test
    void getIssuesByProjectPositiveTets() {
            List<Issue> mockIssues = new ArrayList<>();
            UUID projectId = UUID.randomUUID();
            User owner = new User();
            owner.setEmail("tester@example.com");

            Issue mockIssue = new Issue();
            mockIssue.setProjectId(projectId);
            mockIssue.setIssueId(UUID.randomUUID());
            mockIssue.setOwner(owner);
            mockIssue.setTitle("Issue Title");
            mockIssues.add(mockIssue);

            Issue mockIssue2 = new Issue();
            mockIssue2.setProjectId(projectId);
            mockIssue2.setIssueId(UUID.randomUUID());
            mockIssue2.setOwner(owner);
            mockIssue2.setTitle("Issue Title 2");
            mockIssues.add(mockIssue2);

            when(issueRepository.findByProjectId(projectId)).thenReturn(mockIssues);

            List<Issue> results = issueService.getIssuesByProject(projectId);

            assertNotNull(results);
            assertEquals(2, results.size());
            assertEquals("Issue Title", results.get(0).getTitle());
            assertEquals("Issue Title 2", results.get(1).getTitle());
    }

    @Test
    void getIssuesByProjectEmptyTest() {
        UUID projectId = UUID.randomUUID();
        when(issueRepository.findByProjectId(projectId)).thenReturn(new ArrayList<>());

        List<Issue> result = issueService.getIssuesByProject(projectId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void updateIssuePositiveTest() {
        User owner = new User();
        owner.setEmail("tester@example.com");
        owner.setRole(User.UserRole.TESTER);

        Issue mockIssue = new Issue();
        UUID issueId = UUID.randomUUID();
        mockIssue.setOwner(owner);
        mockIssue.setIssueId(issueId);
        mockIssue.setProjectId(UUID.randomUUID());
        mockIssue.setTitle("Issue 1");
        mockIssue.setDescription("Issue 1 Description");
        mockIssue.setStatus(Issue.IssueStatus.OPEN);
        mockIssue.setSeverity(Issue.IssueSeverity.LOW);
        mockIssue.setPriority(Issue.IssuePriority.LOW);

        when(authService.getAuthLevel("header")).thenReturn(User.UserRole.TESTER);
        when(userRepository.findUserByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(mockIssue));
        when(issueRepository.save(mockIssue)).thenReturn(mockIssue);

        Issue result = issueService.updateIssue(mockIssue, "header");

        assertNotNull(result);
        assertEquals(mockIssue.getIssueId(), result.getIssueId());
        assertEquals(mockIssue.getTitle(), result.getTitle());
    }

    @Test
    void updateIssueNegativeTest_InvalidAuthorization() {
        UUID issueId = UUID.randomUUID();

        User owner = new User();
        owner.setEmail("tester@example.com");

        Issue previousIssue = new Issue();
        previousIssue.setIssueId(issueId);
        previousIssue.setStatus(Issue.IssueStatus.OPEN);
        previousIssue.setProjectId(UUID.randomUUID());

        Issue updatedIssue = new Issue();
        updatedIssue.setIssueId(issueId);
        updatedIssue.setOwner(owner);
        updatedIssue.setStatus(Issue.IssueStatus.IN_PROGRESS);

        when(authService.getAuthLevel("header")).thenReturn(User.UserRole.TESTER);
        when(userRepository.findUserByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(previousIssue));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> issueService.updateIssue(updatedIssue, "header")
        );

        assertEquals("Invalid Authorization", exception.getMessage());

        verify(issueRepository, never()).save(any());
        verify(auditLogService, never()).logIssueAction(any(), any(), any(), any());
    }

    @Test
    void getIssueHistoryPositiveTest() {
        UUID issueId = UUID.randomUUID();

        AuditLog log = new AuditLog();
        List<AuditLog> logs = List.of(log);

        when(auditLogService.getAuditLogsForEntity(
                AuditLog.EntityType.ISSUE,
                issueId.toString()
        )).thenReturn(logs);

        List<AuditLog> result = issueService.getIssueHistory(issueId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(logs, result);
    }

    @Test
    void getIssueHistoryEmptyTest() {
        UUID issueId = UUID.randomUUID();

        List<AuditLog> emptyLogs = new ArrayList<>();

        when(auditLogService.getAuditLogsForEntity(
                AuditLog.EntityType.ISSUE,
                issueId.toString()
        )).thenReturn(emptyLogs);

        List<AuditLog> result = issueService.getIssueHistory(issueId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(auditLogService).getAuditLogsForEntity(
                AuditLog.EntityType.ISSUE,
                issueId.toString()
        );
    }
}
