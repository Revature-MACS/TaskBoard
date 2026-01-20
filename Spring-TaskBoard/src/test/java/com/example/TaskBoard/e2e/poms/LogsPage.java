package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;

public class LogsPage {
    private WebDriver driver;

    @FindBy(css = ".log-card")
    private List<WebElement> logCards;

    @FindBy(css = ".log-count")
    private WebElement logCount;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = ".no-data-message")
    private WebElement noDataMessage;

    @FindBy(id = "entityType")
    private WebElement entityTypeFilter;

    @FindBy(id = "actionType")
    private WebElement actionTypeFilter;

    @FindBy(css = "button")
    private WebElement refreshButton;

    public LogsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openLogsPage() {
        driver.get("http://localhost:4200/logs");
    }

    public int getLogCardsCount() {
        return logCards.size();
    }

    public boolean areLogsDisplayed() {
        return !logCards.isEmpty();
    }

    public String getLogCountText() {
        return logCount.getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
