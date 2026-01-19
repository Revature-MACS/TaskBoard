Feature: User Registration

  Background:
    Given User is on registration page

  Scenario Outline: User can register with valid credentials
    When    The user enters credentials "<email>", "<password>", "<name>", and "<role>"
    And     The user attempts to register
    Then    The user should be redirected to the dashboard

    Examples:
    |   email   |   password    |     name    |   role    |
    | valid@email.com  | password | Test User  | Tester   |
    | valid2@email.com | password2 | Developer User | Developer |
    | valid3@email.com | password3 | Admin User | Admin   |

  Scenario Outline: User can't register with empty details
    When  The user enters "<email>", "<password>", "<name>", and "<role>" but one or more is empty
    And   The user attempts to register
    Then  The user is not redirected to the dashboard

    Examples:
      |   email   |   password    |     name    |   role    |
      |           |   password    |  Test User  |  Tester   |
      | invalid1@email.com |      | Developer User | Developer  |
      | invalid2@email.com | password3 |           | Admin  |
      | invalid3@email.com | password4 | Test User |        |

  Scenario: User can't register because account already exists
    When  The user enters credentials "admin@taskboard.com", "password", "Admin User", and "Admin"
    And   The user attempts to register
    Then  The user is not redirected to the dashboard