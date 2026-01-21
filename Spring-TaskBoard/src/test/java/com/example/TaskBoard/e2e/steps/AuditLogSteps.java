package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.UserRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class AuditLogSteps {

    @Autowired
    private UserRepository userRepository;

    private String token;
    private Response response;

    @Given("an API user is logged in as an ADMIN for audit logs")
    public void an_api_user_is_logged_in_as_an_admin_for_audit_logs() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        User admin = userRepository.findUserByEmail("admin@taskboard.com").orElseThrow();
        Map<String, String> credentials = Map.of("email", admin.getEmail(), "password", "admin123");

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

    @When("the user requests all audit logs via API")
    public void the_user_requests_all_audit_logs_via_api() {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/audit-logs");
    }

    @Then("the API response should contain audit logs")
    public void the_api_response_should_contain_audit_logs() {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }

    @When("the user requests audit logs for entity type {string} via API")
    public void the_user_requests_audit_logs_for_entity_type_via_api(String entityType) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/audit-logs/entity/" + entityType);
    }

    @Then("the API response should contain audit logs for entity type {string}")
    public void the_api_response_should_contain_audit_logs_for_entity_type(String entityType) {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())))
                .body("[0].entityType", equalTo(entityType));
    }

    @When("the user requests audit logs for user {string} via API")
    public void the_user_requests_audit_logs_for_user_via_api(String userEmail) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/audit-logs/user/" + userEmail);
    }

    @Then("the API response should contain audit logs for user {string}")
    public void the_api_response_should_contain_audit_logs_for_user(String userEmail) {
        response.then()
                .statusCode(200)
                .body("$", is(not(empty())))
                .body("[0].performedBy", equalTo(userEmail));
    }
}
