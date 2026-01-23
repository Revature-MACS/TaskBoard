package com.example.TaskBoard.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static com.example.TaskBoard.e2e.fixtures.TestFixtures.*;

public class CommentSteps {
    @Given("the user is logged in and on issue page")
    public void the_user_is_logged_in_and_on_issue_page() {
        commentPage.openCommentPage();
    }
    @Given("user is on an issue page")
    public void user_is_on_an_issue_page() {
        commentPage.openCommentPage();
    }
    @When("the user opens all comments to the issue")
    public void the_user_opens_all_comments_to_the_issue() {
        commentPage.clickLoadComments();
    }
    @Then("all comments for an issue should be opened successfully")
    public void all_comments_for_an_issue_should_be_opened_successfully() {
        commentPage.readComments();
    }

}
