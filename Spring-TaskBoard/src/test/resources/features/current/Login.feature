Feature: User Login

  Scenario: User can log in with valid credentials
    Given the user is on the login page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    Then The user should be redirected to the dashboard

  Scenario Outline: User cannot log in with invalid credentials
    Given the user is on the login page
    When the user enters credentials "<email>" and "<password>"
    And the user attempts to login
    Then  The user is not redirected to the dashboard

    Examples:
    | email | password |
    |       |          |
    |       | password |
    | invalid |        |
    | invalid | password |
