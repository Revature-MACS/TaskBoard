Feature: Project Management E2E

  Scenario: List projects by owner email
    Given a user is logged in as an ADMIN
    When the user requests all projects for owner "admin@taskboard.com"
    Then the response should contain a list of projects

  Scenario: Admin can create a new project
    Given the user is on the login page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    And the user navigates to the project page
    And the user fills the create project form with name "New Test Project", description "Testing creation", and owner "admin@taskboard.com"
    And the user submits the create project form
    Then the project should be created successfully

  Scenario: Admin can update an existing project
    Given the user is on the login page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    And the user navigates to the project page
    And the user fills the update project form with id "11111111-1111-1111-1111-111111111111", name "Updated Project", description "Testing update", and owner "admin@taskboard.com"
    And the user submits the update project form
    Then the project should be updated successfully

  Scenario: Admin can delete a project
    Given the user is on the login page
    When the user enters credentials "admin@taskboard.com" and "admin123"
    And the user attempts to login
    And the user navigates to the project page
    And the user enters project id "11111111-1111-1111-1111-111111111111" to delete
    And the user submits the delete project form
    Then the project should be deleted successfully
