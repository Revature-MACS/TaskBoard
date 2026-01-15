package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends ParentPOM{

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    public LogoutPage(WebDriver driver){super(driver);}

    public void attemptLogout(){logoutButton.click();}
}
