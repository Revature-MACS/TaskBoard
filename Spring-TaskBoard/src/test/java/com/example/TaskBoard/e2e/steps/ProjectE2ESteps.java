package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
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
}
