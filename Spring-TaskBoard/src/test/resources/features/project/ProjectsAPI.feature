Feature: Project API

  Scenario: Get all projects via API
    Given an API user is logged in as an ADMIN
    When the user requests all projects via API
    Then the API response should contain all projects

  Scenario: Get project by ID via API
    Given an API user is logged in as an ADMIN
    When the user requests project with id "33333333-3333-3333-3333-333333333333" via API
    Then the API response should contain the project details

  Scenario: List projects by owner email via API
    Given an API user is logged in as an ADMIN
    When the user requests all projects for owner "admin@taskboard.com" via API
    Then the API response should contain a list of projects

  Scenario: Create a new project via API
    Given an API user is logged in as an ADMIN
    When the user creates a project with name "API Test Project" and description "Created via API" via API
    Then the project should be created successfully via API

  Scenario: Update a project via API
    Given an API user is logged in as an ADMIN
    When the user updates project "22222222-2222-2222-2222-222222222222" with name "Updated API Project" and description "Updated via API" via API
    Then the project should be updated successfully via API

  Scenario: Get assigned users for project via API
    Given an API user is logged in as an ADMIN
    When the user requests assigned users for project "11111111-1111-1111-1111-111111111111" via API
    Then the API response should contain the assigned users

  Scenario: Assign user to project via API
    Given an API user is logged in as an ADMIN
    When the user assigns user "00000000-0000-0000-0000-000000000004" to project "22222222-2222-2222-2222-222222222222" via API
    Then the user should be assigned successfully via API

  Scenario: Unassign user from project via API
    Given an API user is logged in as an ADMIN
    When the user unassigns user "00000000-0000-0000-0000-000000000003" from project "22222222-2222-2222-2222-222222222222" via API
    Then the user should be unassigned successfully via API

  Scenario: Delete a project via API
    Given an API user is logged in as an ADMIN
    When the user deletes project "33333333-3333-3333-3333-333333333333" via API
    Then the project should be deleted successfully via API
