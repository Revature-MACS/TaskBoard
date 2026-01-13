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
    }

    private String getAuthToken() {
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

    @Test
    public void getAllIssuesPositiveTest() {
        String token = getAuthToken();
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
        String token = getAuthToken();
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
    public void createIssuePositiveTest() {
        String token = getAuthToken();

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


}
