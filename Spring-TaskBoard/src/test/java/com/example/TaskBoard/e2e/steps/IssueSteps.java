package com.example.TaskBoard.e2e.steps;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class IssueSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;
    private String token;
    private Response response;

    @Given("a user is logged in as a TESTER")
    public void a_user_is_logged_in_as_a_tester() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        User tester = new User();

        // Ensure test user exists
        if (userRepository.findUserByEmail("tester1@taskboard.com").isEmpty()) {
            tester.setName("Alice Tester");
            tester.setEmail("tester1@taskboard.com");
            tester.setPassword("testerPassword");
            tester.setRole(User.UserRole.TESTER);
            userRepository.save(tester);
        } else {
            tester = userRepository.findUserByEmail("tester1@taskboard.com").get();
        }

        Map<String, String> credentials = Map.of("email", tester.getEmail(), "password", tester.getPassword());

        // Login to get token
        token = given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

}
