package com.example.TaskBoard.e2e.poms;

import com.example.TaskBoard.entity.User.UserRole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends ParentPOM {

    private final String URL = "http://localhost:4200/register";

    public RegisterPage(WebDriver driver){super(driver);}

    @FindBy(id = "title")
    private WebElement registerTitle;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "roleSelect")
    private WebElement roleSelect;

    @FindBy(id = "registerButton")
    private WebElement registerButton;

    private String roleToTitleCaseString(UserRole role){
        if(role == null){
            return "";
        }
        String tempString = role.toString().toLowerCase().substring(1);
        return role.toString().charAt(0) + tempString;
    }

    public void openRegisterPage(){driver.get(URL);}

    public void enterRegistrationDetails(String email, String password, String name, UserRole role){
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        nameInput.sendKeys(name);
        new Select(roleSelect).selectByVisibleText(roleToTitleCaseString(role));
    }

    public void attemptRegister(){registerButton.click();}

    public String getRegistrationTitleText(){return registerTitle.getText();}
}
