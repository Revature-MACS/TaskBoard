package com.example.TaskBoard.api.user;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.entity.User.UserRole;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.ProjectUserRepository;
import com.example.TaskBoard.repository.UserRepository;
import com.example.TaskBoard.util.TokenUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserApiTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;
    @Autowired
    private TokenUtility tokenUtility;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/users";
        
    }

    @BeforeEach
    public void resetDatabase(){
        userRepository.deleteAll();
    }

    private String getAuthToken(User credentials) {
        return given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");
    }

    @Test
    public void getAllUsersTest(){
        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("test@email.com");
        user1.setPassword("password");
        user1.setRole(UserRole.TESTER);

        User user2 = new User();
        user2.setName("Admin User");
        user2.setEmail("admin@email.com");
        user2.setPassword("admin123");
        user2.setRole(UserRole.ADMIN);

        userRepository.save(user1);
        userRepository.save(user2);

        given()
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("size()", is(2))
                .body("[0].email", is(user1.getEmail()))
                .body("[1].email", is(user2.getEmail()));
    }

    @Test
    public void registerUserPositiveTest(){
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        String token = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");


        assertEquals(tokenUtility.extractUserRole(token), user.getRole());

    }

    @Test
    public void registerUserNegativeTest(){
        User invalidUser = new User();
        invalidUser.setEmail("");
        invalidUser.setPassword("");
        invalidUser.setName("WillBeValid");
        invalidUser.setRole(UserRole.TESTER);

        RequestSpecification baseRequest =
                given()
                .contentType(ContentType.JSON);

        // First check to make sure empty email is checked
        baseRequest.body(invalidUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        //Second check to make sure empty password is checked
        invalidUser.setEmail("valid@email.com");
        baseRequest.body(invalidUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        //Finally check for conflict, since the user is added to the database before the request is sent
        invalidUser.setPassword("validPass");
        baseRequest.body(userRepository.save(invalidUser))
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void loginPositiveTest(){
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);
        userRepository.save(user);

        String token = getAuthToken(user);

        assertEquals(tokenUtility.extractUserRole(token), user.getRole());
    }

    @Test
    public void loginNegativeTest(){
        given()
                .contentType(ContentType.JSON)
                .body(new User())
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateUserPositiveTest(){
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        user = userRepository.save(user);
        user.setEmail("new@email.com");
        user.setRole(UserRole.ADMIN);

        given()
                .pathParam("email", "test@email.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/{email}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("email", is(user.getEmail()))
                .body("password", is(user.getPassword()))
                .body("name", is(user.getName()))
                .body("role", is(user.getRole().toString()));
    }

    @Test
    public void updateUserNegativeTest(){
        given()
                .pathParam("email", "test@email.com")
                .contentType(ContentType.JSON)
                .body(new User())
                .when()
                .put("/{email}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

   @Test
    public void deleteUserPositiveTest(){
        final String URI_PATH = "/{email}";

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);
        user = userRepository.save(user);

        assertTrue(userRepository.findUserByEmail(user.getEmail()).isPresent());

        // No such email exists, so no email is removed
        given()
                .pathParam("email", "invalid@email.com")
                .contentType(ContentType.JSON)
                .when()
                .delete(URI_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());

        assertTrue(userRepository.findUserByEmail(user.getEmail()).isPresent());

        given()
                .pathParam("email", user.getEmail())
                .contentType(ContentType.JSON)
                .when()
                .delete(URI_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());

        // Ensure that the data was actually removed
       assertTrue(userRepository.findUserByEmail(user.getEmail()).isEmpty());
    }

    @Test
    public void getAssignedUsersToProjectTest(){
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        User user2 = new User();
        user2.setName("Test User2");
        user2.setEmail("test2@email.com");
        user2.setPassword("password");
        user2.setRole(UserRole.DEVELOPER);

        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@email.com");
        admin.setPassword("admin123");
        admin.setRole(UserRole.ADMIN);

        user = userRepository.save(user);
        user2 = userRepository.save(user2);
        admin = userRepository.save(admin);

        Project project = new Project();
        project.setName("Test Project");
        project.setOwner(admin);
        project.setDescription("This is a test");
        project = projectRepository.save(project);

        projectUserRepository.save(new ProjectUser(user, project));
        projectUserRepository.save(new ProjectUser(user2, project));

        given()
                .pathParam("projectId", project.getProjectId())
                .when()
                .get("/project/{projectId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("size()", is(2))
                .body("[0].email", is(user.getEmail()))
                .body("[1].email", is(user2.getEmail()));
    }

    @Test
    public void assignUnassignUserPositiveTest(){
        final String GET_PATH = "/project/{projectId}";

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@email.com");
        admin.setPassword("admin123");
        admin.setRole(UserRole.ADMIN);

        user = userRepository.save(user);
        admin = userRepository.save(admin);

        Project project = new Project();
        project.setName("Test Project");
        project.setOwner(admin);
        project.setDescription("This is a test");

        project = projectRepository.save(project);

        ProjectUser userAssignment = new ProjectUser(user, project);

        // Test assignment
        given()
                .contentType(ContentType.JSON)
                .body(userAssignment)
                .when()
                .post("/assign")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("user.userID", is(userAssignment.getUser().getUserID().toString()))
                .body("project.projectId", is(userAssignment.getProject().getProjectId().toString()));

        // Ensure that userAssignment was added from database
        given()
                .pathParam("projectId", project.getProjectId())
                .when()
                .get(GET_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("size()", is(1));

        //Test unassignment
        given()
                .contentType(ContentType.JSON)
                .body(userAssignment)
                .when()
                .delete("/unassign")
                .then()
                .statusCode(HttpStatus.OK.value());

        // Ensure that userAssignment was removed from database
        given()
                .pathParam("projectId", project.getProjectId())
                .when()
                .get(GET_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("size()", is(0));
    }

    @Test
    public void assignUnassignUserNegativeTest(){
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRole(UserRole.TESTER);

        User user2 = new User();
        user2.setName("Test User2");
        user2.setEmail("test2@email.com");
        user2.setPassword("password");
        user2.setRole(UserRole.DEVELOPER);

        User invalidUser = new User();
        invalidUser.setUserID(UUID.randomUUID());
        invalidUser.setEmail("invalid@email.com");

        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@email.com");
        admin.setPassword("admin123");
        admin.setRole(UserRole.ADMIN);

        user = userRepository.save(user);
        user2 = userRepository.save(user2);
        admin = userRepository.save(admin);

        Project project = new Project();
        project.setName("Test Project");
        project.setOwner(admin);
        project.setDescription("This is a test");

        Project invalidProject = new Project();
        invalidProject.setProjectId(UUID.randomUUID());
        invalidProject.setOwner(admin);

        project = projectRepository.save(project);

        // Attempt to assign user that doesn't exist to project
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(invalidUser, project))
                .when()
                .post("/assign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        // Attempt to assign user to project that doesn't exist
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(user, invalidProject))
                .when()
                .post("/assign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        projectUserRepository.save(new ProjectUser(user, project));

        // Attempt to assign user to project they are already assigned to
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(user, project))
                .when()
                .post("/assign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        // Attempt to unassign user that doesn't exist from project
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(invalidUser, project))
                .when()
                .delete("/unassign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        // Attempt to unassign user from project that doesn't exist
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(user, invalidProject))
                .when()
                .delete("/unassign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        // Attempt to unassign user who isn't assigned to project
        given()
                .contentType(ContentType.JSON)
                .body(new ProjectUser(user2, project))
                .when()
                .delete("/unassign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());


        //Ensure that user is still assigned to project
        given()
                .pathParam("projectId", project.getProjectId())
                .contentType(ContentType.JSON)
                .body(new ProjectUser(user, project))
                .when()
                .get("/project/{projectId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

}
