package com.example.TaskBoard.api;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
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
public class ProjectApiTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void testSetup() {
        RestAssured.basePath = "/projects";

        projectRepository.deleteAll();

        if (userRepository.findUserByEmail("admin@taskboard.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@taskboard.com");
            admin.setPassword("admin123");
            admin.setRole(User.UserRole.ADMIN);
            userRepository.save(admin);
        }
    }

    private String getAuthToken() {
        User credentials = new User();
        credentials.setEmail("admin@taskboard.com");
        credentials.setPassword("admin123");

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
    public void getAllProjectsPositiveTest() {
        String token = getAuthToken();
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Project project = new Project();
        project.setName("Existing Project");
        project.setOwner(owner);
        projectRepository.save(project);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("size()", is(1))
                .body("[0].name", notNullValue())
                .body("[0].projectId", notNullValue());
    }

    @Test
    public void getAllProjectsEmptyTest() {
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
    public void createProjectPositiveTest() {
        String token = getAuthToken();

        String projectName = "New Project " + UUID.randomUUID();
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Project project = new Project();
        project.setName(projectName);
        project.setDescription("Test project description");
        project.setOwner(owner);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo(projectName))
                .body("projectId", notNullValue());
    }

    @Test
    public void updateProjectPositiveTest() {
        String token = getAuthToken();
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Project project = new Project();
        project.setName("P1");
        project.setDescription("P1 Description");
        project.setOwner(owner);
        projectRepository.save(project);

        project.setName("Updated P1");
        project.setDescription("Updated P1 Description");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .put("/{id}", project.getProjectId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated P1"))
                .body("description", equalTo("Updated P1 Description"));
    }

    @Test
    public void deleteProjectPositiveTest() {
        String token = getAuthToken();
        User owner = userRepository.findUserByEmail("admin@taskboard.com").get();

        Project project = new Project();
        project.setName("P1 Delete");
        project.setOwner(owner);
        projectRepository.save(project);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/{id}", project.getProjectId())
                .then()
                .statusCode(204);
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/{id}", project.getProjectId())
                .then()
                .statusCode(404);
    }
    @Test
    public void deleteProjectNegativeTest() {
        String token = getAuthToken();
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/{id}", UUID.randomUUID())
                .then()
                .statusCode(404);
    }
}