package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class IssueServiceUnitTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditLogService auditLogService;

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
}
