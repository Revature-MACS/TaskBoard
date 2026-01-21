Feature: Audit Logs E2E

  Scenario: Admin can view audit logs
    Given a user is logged in as an ADMIN
    And the user navigates to the logs page
    Then the user should see audit logs displayed
    And the logs page should show at least 4 audit logs

  Scenario: Developer cannot access audit logs page
    Given the user is on the login page
    When the user enters credentials "dev1@taskboard.com" and "dev123"
    And the user attempts to login
    Then The user should be redirected to the dashboard
    When the user attempts to navigate to logs page directly
    Then the user should remain on the dashboard page
