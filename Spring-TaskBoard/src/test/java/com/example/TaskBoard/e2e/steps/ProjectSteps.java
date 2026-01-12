package com.example.TaskBoard.e2e.steps;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProjectSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private String token;
    private Response response;

    @Given("a user is logged in as an ADMIN")
    public void a_user_is_logged_in_as_an_admin() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        // Ensure admin user exists
        if (userRepository.findUserByEmail("admin@taskboard.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@taskboard.com");
            admin.setPassword("admin123");
            admin.setRole(User.UserRole.ADMIN);
            userRepository.save(admin);
        }

        // Ensure at least one project exists for this admin
        if (projectRepository.findByOwner_Email("admin@taskboard.com").isEmpty()) {
            User admin = userRepository.findUserByEmail("admin@taskboard.com").get();
            Project project = new Project();
            project.setName("Integration Test Project");
            project.setDescription("A project for E2E testing");
            project.setOwner(admin);
            projectRepository.save(project);
        }

        token = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"admin@taskboard.com\", \"password\":\"admin123\"}")
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @When("the user requests all projects for owner {string}")
    public void the_user_requests_all_projects_for_owner(String email) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/projects/owner/" + email);
    }

    @Then("the response should contain a list of projects")
    public void the_response_should_contain_a_list_of_projects() {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }
}
