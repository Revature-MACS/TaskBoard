package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.wait;

public class IssuePage extends ParentPOM {

    private final String URL = "http://localhost:4200/issue";

    @FindBy(id = "issueTitle")
    private WebElement issueTitleInput;

    @FindBy(id = "issueDescription")
    private WebElement issueDescriptionInput;

    @FindBy(id = "ownerEmail")
    private WebElement issueOwnerInput;

    @FindBy(id = "projectId")
    private WebElement issueProjectIdInput;

    @FindBy(id = "createIssueSubmit")
    private WebElement createIssueSubmitButton;

    @FindBy(id = "successMessageAddIssue")
    private WebElement successMessageAddIssue;

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

    public void clickCreateIssueSubmit() {
        createIssueSubmitButton.click();
    }

    public String getSuccessMessageAddIssue() {
        return successMessageAddIssue.getText();
    }
}
