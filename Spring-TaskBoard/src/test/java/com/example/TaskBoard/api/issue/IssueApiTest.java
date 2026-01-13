package com.example.TaskBoard.api.issue;

import com.example.TaskBoard.entity.Issue;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.IssueRepository;
import com.example.TaskBoard.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IssueApiTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI= "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void testSetup() {
        RestAssured.basePath = "/issues";

        issueRepository.deleteAll();

        if (userRepository.findUserByEmail("tester@taskboard.com").isEmpty()) {
            User tester = new User();
            tester.setName("Tester User");
            tester.setEmail("tester@taskboard.com");
            tester.setPassword("tester123");
            tester.setRole(User.UserRole.TESTER);
            userRepository.save(tester);
        }

        if (userRepository.findUserByEmail("developer@taskboard.com").isEmpty()) {
            User developer = new User();
            developer.setName("Developer User");
            developer.setEmail("developer@taskboard.com");
            developer.setPassword("developer123");
            developer.setRole(User.UserRole.DEVELOPER);
            userRepository.save(developer);
        }
    }

    private String getTesterAuthToken() {
        User credentials = new User();
        credentials.setEmail("tester@taskboard.com");
        credentials.setPassword("tester123");

        return given()
                .basePath("/users/login")
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private String getDeveloperAuthToken() {
        User credentials = new User();
        credentials.setEmail("developer@taskboard.com");
        credentials.setPassword("developer123");

        return given()
                .basePath("/users/login")
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    public void getAllIssuesPositiveTest() {
        String token = getTesterAuthToken();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();
        UUID projectId = UUID.randomUUID();

        Issue issue = new Issue();
        issue.setProjectId(projectId);
        issue.setOwner(owner);
        issue.setTitle("Existing Issue");
        issue.setDescription("Existing Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);
        issueRepository.save(issue);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("size()", is(1))
                .body("[0].title", notNullValue())
                .body("[0].issueId", notNullValue());

    }

    @Test
    public void getAllIssuesEmptyTest() {
        String token = getTesterAuthToken();
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("size()", is(0));
    }

    @Test
    public void getIssueByIdPositiveTest() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);
        Issue resultIssue = issueRepository.save(issue);

        given()
                .pathParam("issueId", resultIssue.getIssueId())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/{issueId}")
                .then()
                .statusCode(200)
                .body("title", equalTo(resultIssue.getTitle()))
                .body("issueId", equalTo(resultIssue.getIssueId().toString()))
                .body("projectId", notNullValue());
    }

    @Test
    public void getIssueByIdNegativeTestInvalidId() {
        String token = getTesterAuthToken();

        given()
                .pathParam("issueId", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/{issueId}")
                .then()
                .statusCode(400);
    }

    @Test
    public void createIssuePositiveTest() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(issue)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("title", equalTo(issueTitle))
                .body("projectId", notNullValue());
    }

    @Test
    public void createIssueNegativeTestInvalidData() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(issue)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void createIssueNegativeTestInvalidAuthorization() {
        String token = getDeveloperAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("developer@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(issue)
                .when()
                .post()
                .then()
                .statusCode(401);
    }

    @Test
    public void deleteIssuePositiveTest() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);
        Issue resultIssue = issueRepository.save(issue);

        given()
                .pathParam("issueId", resultIssue.getIssueId())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/{issueId}")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteIssueNegativeTestInvalidId() {
        String token = getTesterAuthToken();

        given()
                .pathParam("issueId", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/{issueId}")
                .then()
                .statusCode(404);
    }

    @Test
    public void updateIssuePositiveTest() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);
        Issue resultIssue = issueRepository.save(issue);
        issue.setTitle("Updated Title");
        issue.setDescription("Updated Description");

        given()
                .pathParam("issueId", resultIssue.getIssueId())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(issue)
                .when()
                .put("/{issueId}")
                .then()
                .statusCode(200)
                .body("title", equalTo(issue.getTitle()))
                .body("issueId", equalTo(resultIssue.getIssueId().toString()))
                .body("description", equalTo(issue.getDescription()))
                .body("projectId", notNullValue());
    }

    @Test
    public void updateIssueNegativeTestInvalidId() {
        String token = getTesterAuthToken();

        String issueTitle = "New Issue " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("tester@taskboard.com").get();

        Issue issue = new Issue();
        issue.setProjectId(UUID.randomUUID());
        issue.setOwner(owner);
        issue.setTitle(issueTitle);
        issue.setDescription("Test Issue Description");
        issue.setPriority(Issue.IssuePriority.LOW);
        issue.setSeverity(Issue.IssueSeverity.LOW);
        issue.setStatus(Issue.IssueStatus.OPEN);
        Issue resultIssue = issueRepository.save(issue);

        given()
                .pathParam("issueId", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(issue)
                .when()
                .put("/{issueId}")
                .then()
                .statusCode(404);
    }

}
