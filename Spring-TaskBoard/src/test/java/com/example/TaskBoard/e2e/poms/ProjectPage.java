package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectPage extends ParentPOM {

    private final String URL = "http://localhost:4200/project";

    @FindBy(id = "projectName")
    private WebElement projectNameInput;

    @FindBy(id = "projectDescription")
    private WebElement projectDescriptionInput;

    @FindBy(id = "ownerEmail")
    private WebElement ownerEmailInput;

    @FindBy(id = "createProjectSubmit")
    private WebElement createProjectSubmitButton;

    @FindBy(id = "projectIdInput")
    private WebElement projectIdInput;

    @FindBy(id = "getProjectByIdSubmit")
    private WebElement getProjectByIdSubmitButton;

    @FindBy(id = "fetchAllProjectsButton")
    private WebElement fetchAllProjectsButton;

    @FindBy(id = "ownerEmailSearch")
    private WebElement ownerEmailSearchInput;

    @FindBy(id = "getProjectsByOwnerSubmit")
    private WebElement getProjectsByOwnerSubmitButton;

    @FindBy(id = "deleteProjectIdInput")
    private WebElement deleteProjectIdInput;

    @FindBy(id = "deleteProjectSubmit")
    private WebElement deleteProjectSubmitButton;

    @FindBy(id = "updateProjectIdInput")
    private WebElement updateProjectIdInput;

    @FindBy(id = "updateProjectName")
    private WebElement updateProjectNameInput;

    @FindBy(id = "updateProjectDescription")
    private WebElement updateProjectDescriptionInput;

    @FindBy(id = "updateOwnerEmail")
    private WebElement updateOwnerEmailInput;

    @FindBy(id = "updateProjectSubmit")
    private WebElement updateProjectSubmitButton;

    @FindBy(id = "successMessageAssign")
    private WebElement successMessageAssign;

    @FindBy(id = "successMessageUnassign")
    private WebElement successMessageUnassign;

    @FindBy(id = "successMessageAddProject")
    private WebElement successMessageAddProject;

    @FindBy(id = "successMessageDeleteProject")
    private WebElement successMessageDeleteProject;

    @FindBy(id = "successMessageUpdateProject")
    private WebElement successMessageUpdateProject;

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public void openProjectPage() {
        driver.get(URL);
    }

    public void enterCreateProjectForm(String name, String description, String ownerEmail) {
        projectNameInput.sendKeys(name);
        projectDescriptionInput.sendKeys(description);
        ownerEmailInput.sendKeys(ownerEmail);
    }

    public void clickCreateProjectSubmit() {
        createProjectSubmitButton.click();
    }

    public void enterProjectIdToFetch(String id) {
        projectIdInput.sendKeys(id);
    }

    public void clickGetProjectByIdSubmit() {
        getProjectByIdSubmitButton.click();
    }

    public void clickFetchAllProjects() {
        fetchAllProjectsButton.click();
    }

    public void enterOwnerEmailToSearch(String email) {
        ownerEmailSearchInput.sendKeys(email);
    }

    public void clickGetProjectsByOwnerSubmit() {
        getProjectsByOwnerSubmitButton.click();
    }

    public void enterProjectIdToDelete(String id) {
        deleteProjectIdInput.sendKeys(id);
    }

    public void clickDeleteProjectSubmit() {
        deleteProjectSubmitButton.click();
    }

    public void fillUpdateProjectForm(String id, String name, String description, String ownerEmail) {
        updateProjectIdInput.sendKeys(id);
        updateProjectNameInput.sendKeys(name);
        updateProjectDescriptionInput.sendKeys(description);
        updateOwnerEmailInput.sendKeys(ownerEmail);
    }

    public void clickUpdateProjectSubmit() {
        updateProjectSubmitButton.click();
    }

    public String getSuccessMessageAddProject() {
        return successMessageAddProject.getText();
    }

    public String getSuccessMessageDeleteProject() {
        return successMessageDeleteProject.getText();
    }

    public String getSuccessMessageUpdateProject() {
        return successMessageUpdateProject.getText();
    }
}
