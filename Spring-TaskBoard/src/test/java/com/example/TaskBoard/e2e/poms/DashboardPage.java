package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends ParentPOM {

    private final String URL = "http://localhost:4200/dashboard";

    @FindBy(id = "dashboardTitle")
    private WebElement dashboardTitle;

    @FindBy(id = "loadProjectsButton")
    private WebElement loadProjectsButton;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void openDashboardPage() {
        driver.get(URL);
    }

    public String getDashboardTitleText() {
        return dashboardTitle.getText();
    }

    public void clickLoadProjects() {
        loadProjectsButton.click();
    }
}
