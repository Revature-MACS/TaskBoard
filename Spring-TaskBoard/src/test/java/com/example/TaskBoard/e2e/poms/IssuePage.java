package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.wait;

public class IssuePage extends ParentPOM {

    private final String URL = "http://localhost:4200/issue";

    @FindBy(id = "createIssueTitle")
    private WebElement issueTitleInput;

    @FindBy(id = "createIssueDescription")
    private WebElement issueDescriptionInput;

    @FindBy(id = "createOwnerEmail")
    private WebElement issueOwnerInput;

    @FindBy(id = "createProjectId")
    private WebElement issueProjectIdInput;

    @FindBy(id = "createIssueSubmit")
    private WebElement createIssueSubmitButton;

    @FindBy(id = "successMessageAddIssue")
    private WebElement successMessageAddIssue;

    @FindBy(id = "fetchIssueId")
    private WebElement fetchIssueData;

    @FindBy(id = "fetchIssueSubmit")
    private WebElement fetchIssueSubmitButton;

    @FindBy(id = "deleteIssueId")
    private WebElement deleteIssueInput;

    @FindBy(id = "deleteIssueSubmitButton")
    private WebElement deleteIssueSubmitButton;

    @FindBy(id = "successMessageDeleteIssue")
    private WebElement deleteIssueSuccessMessage;

    @FindBy(id = "fetchAllIssuesButton")
    private WebElement fetchAllIssuesButton;

    @FindBy(id = "updateOwnerEmail")
    private WebElement updateOwnerEmailInput;

    @FindBy(id = "updateIssueId")
    private WebElement updateIssueIdInput;

    @FindBy(id = "updateIssueTitle")
    private WebElement updateIssueTitleInput;

    @FindBy(id = "updateIssueDescription")
    private WebElement updateIssueDescriptionInput;

    @FindBy(id = "updateIssueSubmitButton")
    private WebElement updateIssueSubmitButton;

    @FindBy(id = "updateIssueSuccessMessage")
    private WebElement updateIssueSuccessMessage;

    @FindBy(id = "errorMessageAddIssue")
    private WebElement createIssueFailureMessage;

    @FindBy(id = "errorMessageFetchIssue")
    private WebElement fetchIssueFailureMessage;

    @FindBy(id = "updateIssueStatus")
    private WebElement updateIssueSelectElement;

    @FindBy(id = "updateIssueErrorMessage")
    private WebElement updateIssueErrorMessage;

    @FindBy(id = "failMessageDeleteIssue")
    private WebElement failMessageDeleteIssue;

    @FindBy(id = "filterTextInput")
    private WebElement filterTextInput;

    @FindBy(id = "submitFilterButton")
    private WebElement filterSubmitButton;

    @FindBy(id = "filterPrioritySelection")
    private WebElement filterPrioritySelection;

    public IssuePage(WebDriver driver) {
        super(driver);
    }

    public void openIssuePage() {driver.get(URL);}

    public void enterCreateIssueForm(String ownerEmail, String projectId, String issueTitle, String issueDescription) {
        issueOwnerInput.sendKeys(ownerEmail);
        issueProjectIdInput.sendKeys(projectId);
        issueTitleInput.sendKeys(issueTitle);
        issueDescriptionInput.sendKeys(issueDescription);
    }

    public void enterFetchIssueForm(String issueId) {
        fetchIssueData.sendKeys(issueId);
    }

    public void clickCreateIssueSubmit() {
        createIssueSubmitButton.click();
    }

    public String getSuccessMessageAddIssue() {
        return successMessageAddIssue.getText();
    }

    public void clickFetchIssueSubmit() {
        fetchIssueSubmitButton.click();
    }

    public void enterDeleteIssueForm(String issueId) {
        deleteIssueInput.sendKeys(issueId);
    }

    public void clickDeleteIssueSubmitButton() {
        deleteIssueSubmitButton.click();
    }

    public String getSuccessMessageDeleteIssue() {
        return deleteIssueSuccessMessage.getText();
    }

    public void clickFetchIssuesButton() {
        fetchAllIssuesButton.click();
    }

    public void enterUpdateIssueForm(String email, String issueId, String title, String description) {
        updateOwnerEmailInput.sendKeys(email);
        updateIssueIdInput.sendKeys(issueId);
        updateIssueTitleInput.sendKeys(title);
        updateIssueDescriptionInput.sendKeys(description);
    }

    public void clickUpdateIssueSubmitButton() {
        updateIssueSubmitButton.click();
    }

    public String getUpdateIssueSuccessMessage() {
        return updateIssueSuccessMessage.getText();
    }

    public String getFailureMessageAddIssue() {
        return createIssueFailureMessage.getText();
    }

    public String getFailureMessageFetchIssue() {
        return fetchIssueFailureMessage.getText();
    }

    public void enterUpdateIssueFormIncorrectStatus(String email, String issueId, String title, String description, String status) {
        Select select = new Select(updateIssueSelectElement);

        updateOwnerEmailInput.sendKeys(email);
        updateIssueIdInput.sendKeys(issueId);
        updateIssueTitleInput.sendKeys(title);
        updateIssueDescriptionInput.sendKeys(description);
        select.selectByVisibleText(status);
    }

    public String getFailureMessageUpdateIssue() {
        return updateIssueErrorMessage.getText();
    }

    public String getFailureMessageDeleteIssue() {
        return failMessageDeleteIssue.getText();
    }

    public void enterFilterTitleForm(String filterTitle) {
        filterTextInput.sendKeys(filterTitle);
    }

    public void clickFilterIssueButton() {
        filterSubmitButton.click();
    }

    public void enterFilterPriorityForm(String filterPriority) {
        Select select = new Select(filterPrioritySelection);
        select.selectByVisibleText(filterPriority);
    }
}
