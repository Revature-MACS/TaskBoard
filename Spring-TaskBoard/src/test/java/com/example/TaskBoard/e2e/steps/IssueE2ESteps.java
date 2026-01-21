package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    @And("the user enters information id {string}")
    public void theUserEntersInformationId(String issueId) {
        issuePage.enterFetchIssueForm(issueId);
    }

    @And("the user clicks submit in fetch issue section")
    public void theUserClicksSubmitInFetchIssueSection() {
        issuePage.clickFetchIssueSubmit();
    }

    @Then("the issue should be fetched")
    public void theIssueShouldBeFetched() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fetchIssueData")));
        WebElement issueContainer = driver.findElement(By.id("fetchIssueData"));
        String issueText = issueContainer.getText();
        Assertions.assertTrue(issueText.contains("Shopping cart not persisting"));
    }

    @And("the user enters information to delete id {string}")
    public void theUserEntersInformationToDeleteId(String issueId) {
        issuePage.enterDeleteIssueForm(issueId);
    }

    @And("the user clicks submit in the delete issue section")
    public void theUserClicksSubmitInTheDeleteIssueSection() {
        issuePage.clickDeleteIssueSubmitButton();
    }

    @Then("the issue should be deleted")
    public void theIssueShouldBeDeleted() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageDeleteIssue")));
        String message = issuePage.getSuccessMessageDeleteIssue();
        Assertions.assertTrue(message.contains("Deleted"));
    }

    @And("the user clicks the fetch issues button")
    public void theUserClicksTheFetchIssuesButton() {
        issuePage.clickFetchIssuesButton();
    }

    @Then("the issues should be fetched")
    public void theIssuesShouldBeFetched() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fetchAllIssues")));
        WebElement issuesContainer = driver.findElement(By.id("fetchAllIssues"));
        String issuesText = issuesContainer.getText();
        System.out.println(issuesText);
        Assertions.assertTrue(issuesText.contains("Test Issue"));
    }

    @And("the user enters update information email {string}  and issue id {string} and title {string} and description {string}")
    public void theUserEntersUpdateInformationEmailAndIssueIdAndTitleAndDescription(String email, String issueId, String title, String description) {
        issuePage.enterUpdateIssueForm(email, issueId, title, description);
    }

    @And("the user clicks the update issue submit button")
    public void theUserClicksTheUpdateIssueSubmitButton() {
        issuePage.clickUpdateIssueSubmitButton();
    }

    @Then("the issue should be updated")
    public void theIssueShouldBeUpdated() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("updateIssueSuccessMessage")));
        String message = issuePage.getUpdateIssueSuccessMessage();
        Assertions.assertTrue(message.contains("Updated"));
    }

}
