package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class LoginSteps {

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        loginPage.openLoginPage();
    }

    @When("the user enters credentials {string} and {string}")
    public void the_user_enters_credentials(String email, String password) {
        loginPage.enterCredentials(email, password);
    }

    @And("the user attempts to login")
    public void the_user_attempts_to_login() {
        loginPage.attemptLogin();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            wait.until(d -> d.getCurrentUrl().contains("dashboard") ||
                    !d.findElements(By.className("error-message")).isEmpty());
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }

    @Then("The user should be redirected to the dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        wait.until(ExpectedConditions.urlContains("dashboard"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardTitle")));
        Assertions.assertEquals("Dashboard", dashboardPage.getDashboardTitleText());
    }
}
