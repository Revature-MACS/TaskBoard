package com.example.TaskBoard.e2e.steps;

import com.example.TaskBoard.entity.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class RegisterSteps {

    @Given("User is on registration page")
    public void user_is_on_registration_page() {
        registerPage.openRegisterPage();
    }

    @When("The user enters credentials {string}, {string}, {string}, and {string}")
    public void the_user_enters_credentials_and(String email, String password, String name, String role) {
        registerPage.enterRegistrationDetails(email, password, name, User.matchStringToUserRole(role));
    }

    @When("The user attempts to register")
    public void the_user_attempts_to_register() {
        registerPage.attemptRegister();
    }

    // Negative tests
    @When("The user enters {string}, {string}, {string}, and {string} but one or more is empty")
    public void the_user_enters_and_but_one_or_more_is_empty
        (String email, String password, String name, String role) {

        if(role == null || role.isEmpty()){
            assertThrows(NoSuchElementException.class, () -> {
                registerPage.enterRegistrationDetails(email, password,
                        name, User.matchStringToUserRole(role));
            });
        }

        else{
            registerPage.enterRegistrationDetails(email, password,
                    name, User.matchStringToUserRole(role));
        }
    }

    @Then("The user is not redirected to the dashboard")
    public void the_user_is_not_redirected_to_the_dashboard() {
        assertFalse(driver.getCurrentUrl().contains("dashboard"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
    }
}
