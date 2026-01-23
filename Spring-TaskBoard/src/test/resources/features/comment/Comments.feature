Feature: Comments for Issues

  Scenario: Add comment to an issue
    Given the user is logged in and on issue page
    And   user is on an issue page
    When  the user add comments to the issue
    Then  the comments should be added successfully

  Scenario: Read comments attached to an issue
    Given the user is logged in and on issue page
    And   user is on an issue page
    When  the user opens all comments to the issue
    Then  all comments for an issue should be opened successfully
