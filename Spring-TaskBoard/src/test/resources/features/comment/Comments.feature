Feature: Comments for Issues

  Background:
    Given the user is on the issue page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    Then The user should be redirected to the dashboard

  Scenario:
    When The user attempts to logout
    Then The user should be redirected to login page