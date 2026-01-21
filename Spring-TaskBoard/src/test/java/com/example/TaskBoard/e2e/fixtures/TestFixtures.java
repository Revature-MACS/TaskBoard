package com.example.TaskBoard.e2e.fixtures;

import com.example.TaskBoard.e2e.poms.*;
import io.cucumber.java.After;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestFixtures {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static LoginPage loginPage;
    public static RegisterPage registerPage;
    public static LogoutPage logoutPage;
    public static ProjectPage projectPage;
    public static DashboardPage dashboardPage;
    public static IssuePage issuePage;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Initialize POMs
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        logoutPage = new LogoutPage(driver);
        projectPage = new ProjectPage(driver);
        dashboardPage = new DashboardPage(driver);
        issuePage = new IssuePage(driver);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
