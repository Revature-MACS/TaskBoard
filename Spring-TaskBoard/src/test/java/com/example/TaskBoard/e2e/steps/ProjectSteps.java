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

import java.util.Map;
import java.util.UUID;

public class ProjectSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;
    private String token;
    private Response response;

    @Given("an API user is logged in as an ADMIN")
    public void a_user_is_logged_in_as_an_admin() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        User admin = new User();

        // Ensure admin user exists
        if (userRepository.findUserByEmail("admin@taskboard.com").isEmpty()) {
            admin.setName("Admin User");
            admin.setEmail("admin@taskboard.com");
            admin.setPassword("admin123");
            admin.setRole(User.UserRole.ADMIN);
            userRepository.save(admin);
        } else {
            admin = userRepository.findUserByEmail("admin@taskboard.com").get();
        }

        // Ensure at least one project exists for this admin
        if (projectRepository.findByOwner_Email("admin@taskboard.com").isEmpty()) {
            Project project = new Project();
            project.setProjectId(UUID.randomUUID());
            project.setName("Integration Test Project");
            project.setDescription("A project for E2E testing");
            project.setOwner(admin);
            projectRepository.save(project);
        }

        Map<String, String> credentials = Map.of("email", admin.getEmail(), "password", admin.getPassword());

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

    @When("the user requests all projects for owner {string} via API")
    public void the_user_requests_all_projects_for_owner(String email) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/projects/owner/" + email);
    }

    @Then("the API response should contain a list of projects")
    public void the_response_should_contain_a_list_of_projects() {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }

    @When("the user creates a project with name {string} and description {string} via API")
    public void the_user_creates_a_project_via_api(String name, String description) {
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Map<String, Object> projectBody = Map.of(
                "name", name,
                "description", description,
                "owner", Map.of("email", owner.getEmail()));

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(projectBody)
                .when()
                .post("/projects");
    }

    @Then("the project should be created successfully via API")
    public void the_project_should_be_created_successfully_via_api() {
        response.then()
                .statusCode(201)
                .body("name", is(not(emptyOrNullString())))
                .body("projectId", is(not(emptyOrNullString())));
    }

    @When("the user requests all projects via API")
    public void the_user_requests_all_projects_via_api() {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/projects");
    }

    @Then("the API response should contain all projects")
    public void the_api_response_should_contain_all_projects() {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }

    @When("the user requests project with id {string} via API")
    public void the_user_requests_project_by_id_via_api(String projectId) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/projects/" + projectId);
    }

    @Then("the API response should contain the project details")
    public void the_api_response_should_contain_project_details() {
        response.then()
                .statusCode(200)
                .body("projectId", is(not(emptyOrNullString())))
                .body("name", is(not(emptyOrNullString())));
    }

    @When("the user updates project {string} with name {string} and description {string} via API")
    public void the_user_updates_project_via_api(String projectId, String name, String description) {
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Map<String, Object> projectBody = Map.of(
                "name", name,
                "description", description,
                "owner", Map.of("email", owner.getEmail()));

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(projectBody)
                .when()
                .put("/projects/" + projectId);
    }

    @Then("the project should be updated successfully via API")
    public void the_project_should_be_updated_successfully_via_api() {
        response.then()
                .statusCode(200)
                .body("name", is(not(emptyOrNullString())));
    }

    @When("the user deletes project {string} via API")
    public void the_user_deletes_project_via_api(String projectId) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/projects/" + projectId);
    }

    @Then("the project should be deleted successfully via API")
    public void the_project_should_be_deleted_successfully_via_api() {
        response.then()
                .statusCode(204);
    }

    @When("the user assigns user {string} to project {string} via API")
    public void the_user_assigns_user_to_project_via_api(String userId, String projectId) {
        Map<String, Object> assignmentBody = Map.of(
                "user", Map.of("userID", userId),
                "project", Map.of("projectId", projectId));

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(assignmentBody)
                .when()
                .post("/users/assign");
    }

    @Then("the user should be assigned successfully via API")
    public void the_user_should_be_assigned_successfully_via_api() {
        response.then()
                .statusCode(200);
    }

    @When("the user unassigns user {string} from project {string} via API")
    public void the_user_unassigns_user_from_project_via_api(String userId, String projectId) {
        Map<String, Object> assignmentBody = Map.of(
                "user", Map.of("userID", userId),
                "project", Map.of("projectId", projectId));

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(assignmentBody)
                .when()
                .delete("/users/unassign");
    }

    @Then("the user should be unassigned successfully via API")
    public void the_user_should_be_unassigned_successfully_via_api() {
        response.then()
                .statusCode(200);
    }

    @When("the user requests assigned users for project {string} via API")
    public void the_user_requests_assigned_users_via_api(String projectId) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/project/" + projectId);
    }

    @Then("the API response should contain the assigned users")
    public void the_api_response_should_contain_assigned_users() {
        response.then()
                .statusCode(200)
                .body("$", is(not(nullValue())));
    }
}
