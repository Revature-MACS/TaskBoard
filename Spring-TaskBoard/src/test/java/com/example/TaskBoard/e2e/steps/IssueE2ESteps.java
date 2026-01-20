package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;
import static com.example.TaskBoard.e2e.fixtures.TestFixtures.issuePage;
import static com.example.TaskBoard.e2e.fixtures.TestFixtures.wait;

public class IssueE2ESteps {

    @When("the user navigates to the issue page")
    public void theUserNavigatesToTheIssuePage() {
        issuePage.openIssuePage();
        wait.until(ExpectedConditions.urlContains("issue"));
    }

    @And("the user enters information email {string} and project Id {string} and title {string} and description {string}")
    public void theUserEntersInformationEmailAndProjectIdAndTitleAndDescription(String email, String projectId, String title, String description) {
        issuePage.enterCreateIssueForm(email, projectId, title, description);
    }

    @And("the user clicks submit")
    public void theUserClicksSubmit()
    {
        issuePage.clickCreateIssueSubmit();
    }

    @Then("then issue should be created")
    public void thenIssueShouldBeCreated() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageAddIssue")));
        String message = issuePage.getSuccessMessageAddIssue();
        Assertions.assertTrue(message.contains("Created"));
    }
}
