package com.example.TaskBoard.service;

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
import static org.mockito.Mockito.when;

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
}
