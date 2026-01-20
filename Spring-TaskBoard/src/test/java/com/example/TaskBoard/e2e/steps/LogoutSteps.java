package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class LogoutSteps {
    @When("The user attempts to logout")
    public void the_user_attempts_to_logout() {
        logoutPage.attemptLogout();
    }
    @Then("The user should be redirected to login page")
    public void the_user_should_be_redirected_to_login_page() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton")));
        Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
    }
}
