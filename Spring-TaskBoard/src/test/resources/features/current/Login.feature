Feature: User Login

  Scenario: User can log in with valid credentials
    Given the user is on the login page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    Then The user should be redirected to the dashboard
