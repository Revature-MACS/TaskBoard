package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class AuditLogE2ESteps {

    @And("the user navigates to the logs page")
    public void the_user_navigates_to_the_logs_page() {
        logsPage.openLogsPage();
        wait.until(ExpectedConditions.urlContains("logs"));
    }

    @And("the user attempts to navigate to logs page directly")
    public void the_user_attempts_to_navigate_to_logs_page_directly() {
        logsPage.openLogsPage();
        // Wait a moment for potential redirect
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("the user should remain on the dashboard page")
    public void the_user_should_remain_on_the_dashboard_page() {
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("dashboard"),
                String.format("Expected user to remain on dashboard but was on: %s", currentUrl));
    }

    @Then("the user should see audit logs displayed")
    public void the_user_should_see_audit_logs_displayed() {
        wait.until(driver -> logsPage.areLogsDisplayed());
        Assertions.assertTrue(logsPage.areLogsDisplayed(), "Expected audit logs to be displayed");
        Assertions.assertTrue(logsPage.getLogCardsCount() > 0, "Expected at least one audit log to be displayed");
    }

    @Then("the logs page should show at least {int} audit logs")
    public void the_logs_page_should_show_at_least_audit_logs(int minCount) {
        wait.until(driver -> logsPage.getLogCardsCount() >= minCount);
        int actualCount = logsPage.getLogCardsCount();
        Assertions.assertTrue(actualCount >= minCount,
                String.format("Expected at least %d audit logs but found %d", minCount, actualCount));
    }
}
