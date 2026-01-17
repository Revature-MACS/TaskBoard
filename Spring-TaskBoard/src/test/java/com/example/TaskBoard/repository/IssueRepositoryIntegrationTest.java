package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IssueRepositoryIntegrationTest {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Autowired
    public IssueRepositoryIntegrationTest(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void findByProjectId() {
        final UUID PROJECT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

        User owner = new User();
        owner.setEmail("tester@example.com");
        owner.setName("Test User");
        owner.setPassword("password");
        owner.setRole(User.UserRole.TESTER);
        userRepository.save(owner);

        String issueTitle = "Test Issue";
        String issueDescription = "Test Issue Description";
        Issue issue = new Issue();
        issue.setOwner(owner);
        issue.setProjectId(PROJECT_ID);
        issue.setTitle(issueTitle);
        issue.setDescription(issueDescription);
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);

        UUID issueId = issueRepository.save(issue).getIssueId();

        List<Issue> result = issueRepository.findByProjectId(PROJECT_ID);
        assertTrue(result.size() == 1);
        assertEquals(issueId, result.get(0).getIssueId());
        assertEquals(issueTitle, result.get(0).getTitle());
    }
}
