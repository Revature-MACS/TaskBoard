Feature: Projects Management

  Scenario: List projects by owner email
    Given a user is logged in as an ADMIN
    When the user requests all projects for owner "admin@taskboard.com"
    Then the response should contain a list of projects
