package com.example.TaskBoard.e2e.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.example.TaskBoard.e2e.fixtures.TestFixtures.driver;

public class CommentPage extends ParentPOM{
    private final String URL = "http://localhost:4200/fetch-issue?id=*";

    @FindBy(id = "loadCommentsButton")
    private WebElement loadCommentsButton;

    @FindBy(id = "commentsSection")
    private WebElement commentsSection;

    @FindBy(id = "addButton")
    private WebElement addNewComment;

    @FindBy(id = "commentText")
    private WebElement commentTextInput;

    @FindBy(id = "add-new-comment")
    private WebElement addNewCommentButton;

    public CommentPage(WebDriver driver) {
        super(driver);
    }

    public void openCommentPage() {
        driver.get(URL);
    }

    public void clickLoadComments() {
        loadCommentsButton.click();
    }

    public void clickAddComment() {
        addNewComment.click();
    }

    public void addNewComment(String comment) {
        commentTextInput.sendKeys(comment);
    }

    public void submitNewComment() {
        addNewCommentButton.click();
    }

    public void readComments() {
        commentsSection.getSize();
    }

}
