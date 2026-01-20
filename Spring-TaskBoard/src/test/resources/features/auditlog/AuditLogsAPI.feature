Feature: Audit Logs API

  Scenario: Get all audit logs via API
    Given an API user is logged in as an ADMIN for audit logs
    When the user requests all audit logs via API
    Then the API response should contain audit logs

  Scenario: Get audit logs by entity type via API
    Given an API user is logged in as an ADMIN for audit logs
    When the user requests audit logs for entity type "PROJECT" via API
    Then the API response should contain audit logs for entity type "PROJECT"

  Scenario: Get audit logs by user via API
    Given an API user is logged in as an ADMIN for audit logs
    When the user requests audit logs for user "admin@taskboard.com" via API
    Then the API response should contain audit logs for user "admin@taskboard.com"
