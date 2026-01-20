package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class ProjectE2ESteps {

    @And("the user navigates to the project page")
    public void the_user_navigates_to_the_project_page() {
        projectPage.openProjectPage();
        wait.until(ExpectedConditions.urlContains("project"));
    }

    @And("the user fills the create project form with name {string}, description {string}, and owner {string}")
    public void the_user_fills_the_create_project_form(String name, String description, String owner) {
        projectPage.enterCreateProjectForm(name, description, owner);
    }

    @And("the user submits the create project form")
    public void the_user_submits_the_create_project_form() {
        projectPage.clickCreateProjectSubmit();
    }

    @Then("the project should be created successfully")
    public void the_project_should_be_created_successfully() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageAddProject")));
        String message = projectPage.getSuccessMessageAddProject();
        Assertions.assertTrue(message.contains("Created"));
    }

    @And("the user fills the update project form with id {string}, name {string}, description {string}, and owner {string}")
    public void the_user_fills_the_update_project_form(String id, String name, String description, String owner) {
        projectPage.fillUpdateProjectForm(id, name, description, owner);
    }

    @And("the user submits the update project form")
    public void the_user_submits_the_update_project_form() {
        projectPage.clickUpdateProjectSubmit();
    }

    @Then("the project should be updated successfully")
    public void the_project_should_be_updated_successfully() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageUpdateProject")));
        String message = projectPage.getSuccessMessageUpdateProject();
        Assertions.assertTrue(message.contains("Updated"));
    }

    @And("the user enters project id {string} to delete")
    public void the_user_enters_project_id_to_delete(String id) {
        projectPage.enterProjectIdToDelete(id);
    }

    @And("the user submits the delete project form")
    public void the_user_submits_the_delete_project_form() {
        projectPage.clickDeleteProjectSubmit();
    }

    @Then("the project should be deleted successfully")
    public void the_project_should_be_deleted_successfully() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageDeleteProject")));
        String message = projectPage.getSuccessMessageDeleteProject();
        Assertions.assertTrue(message.contains("Deleted"));
    }

    @Given("a user is logged in as an ADMIN")
    public void a_user_is_logged_in_as_an_admin() {
        loginPage.openLoginPage();
        loginPage.enterCredentials("admin@taskboard.com", "admin123");
        loginPage.attemptLogin();
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    @When("the user requests all projects for owner {string}")
    public void the_user_requests_all_projects_for_owner(String email) {
        projectPage.openProjectPage();
        projectPage.enterOwnerEmailToSearch(email);
        projectPage.clickGetProjectsByOwnerSubmit();
    }

    @Then("the response should contain a list of projects")
    public void the_response_should_contain_a_list_of_projects() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("projectsByOwnerEmail")));
        WebElement projectsContainer = driver.findElement(By.id("projectsByOwnerEmail"));
        String projectsText = projectsContainer.getText();
        Assertions.assertTrue(
                projectsText.contains("Integration Test Project") || projectsText.contains("Fixed Project")
                        || projectsText.contains("E-Commerce Platform"),
                "Expected project list to contain 'Integration Test Project', 'Fixed Project' or 'E-Commerce Platform' but found: "
                        + projectsText);
    }

    @When("the user assigns user with id {string} to project with id {string}")
    public void the_user_assigns_user_with_id_to_project_with_id(String userId, String projectId) {
        projectPage.enterAssignUserDetails(userId, projectId);
        projectPage.clickAssignUserSubmit();
    }

    @Then("the user should be assigned successfully")
    public void the_user_should_be_assigned_successfully() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.id("successMessageAssign")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("errorMessageAssign"))));
        } catch (org.openqa.selenium.TimeoutException e) {
            Assertions.fail("Timed out waiting for assignment result (success or error message).");
        }

        if (projectPage.isErrorMessageAssignDisplayed()) {
            Assertions.fail("Assignment failed with error: " + projectPage.getErrorMessageAssign());
        }

        String message = projectPage.getSuccessMessageAssign();
        Assertions.assertTrue(message.contains("assigned"),
                "Expected success message to contain 'assigned' but found: " + message);
    }

    @When("the user removes user with id {string} from project with id {string}")
    public void the_user_removes_user_with_id_from_project_with_id(String userId, String projectId) {
        projectPage.enterUnassignUserDetails(userId, projectId);
        projectPage.clickUnassignUserSubmit();
    }

    @Then("the user should be removed successfully")
    public void the_user_should_be_removed_successfully() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.id("successMessageUnassign")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("errorMessageUnassign"))));
        } catch (org.openqa.selenium.TimeoutException e) {
            Assertions.fail("Timed out waiting for unassignment result (success or error message).");
        }

        if (projectPage.isErrorMessageUnassignDisplayed()) {
            Assertions.fail("Unassignment failed with error: " + projectPage.getErrorMessageUnassign());
        }

        String message = projectPage.getSuccessMessageUnassign();
        Assertions.assertTrue(message.contains("removed"),
                "Expected success message to contain 'removed' but found: " + message);
    }
}
